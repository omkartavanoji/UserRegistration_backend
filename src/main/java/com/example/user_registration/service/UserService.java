package com.example.user_registration.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.user_registration.dto.Userdto;
import com.example.user_registration.helper.EncryptUsername;
import com.example.user_registration.helper.PasswordHashing;
import com.example.user_registration.repository.UserRepository;

/**
 * Service class for handling user-related operations
 */
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * Registers a new user in the system
	 * @param userdto The user data transfer object containing user information
	 * @param bindingResult Contains the validation results
	 * @return ResponseEntity with success message or validation errors
	 */
	public ResponseEntity<Object> addUser(Userdto userdto, BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		if (userRepository.existsByEmail(userdto.getEmail())) {
			bindingResult.rejectValue("email", "error.email", "User With This Email Already Exists");
		}

		if (userRepository.existsByUserName(userdto.getUserName())) {
			bindingResult.rejectValue("userName", "error.userName", "User With This Username Already Exists");
		}

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		String username = EncryptUsername.encrypt(userdto.getUserName());
		String password = PasswordHashing.hashPassword(userdto.getPassword());
		System.out.println(password);
		userdto.setUserName(username);
		userdto.setPassword(password);
		userRepository.save(userdto);
		return new ResponseEntity<>("User Registered Successfully with USER ID: " + "" + userdto.getId() ,HttpStatus.CREATED);
	}

	/**
	 * Retrieves a user by their ID
	 * @param id The user ID
	 * @return User information or error message if user doesn't exist
	 */
	public Object getUserById(int id) {
		if (userRepository.existsById(id)) {
			return userRepository.findById(id);
		} else {
			return "User Does Not Exists";
		}
	}

	/**
	 * Retrieves a user by their username
	 * @param userName The username to search for
	 * @return User information or error message if user doesn't exist
	 */
	public Object getUserByName(String userName) {
		userName = EncryptUsername.encrypt(userName);
		if (userRepository.existsByUserName(userName)) {
			return userRepository.findByUserName(userName);
		} else {
			return "User Does Not Exists";
		}
	}

	/**
	 * Retrieves users by their first name
	 * @param firstName The first name to search for
	 * @return List of users with matching first name
	 */
	public List<Userdto> getUserByFirstName(String firstName) {
		List<Userdto> list = userRepository.findByFirstName(firstName);
		if (list.isEmpty()) {
			return list;
		} else {
			return list;
		}
	}

	/**
	 * Retrieves users by their last name
	 * @param lastName The last name to search for
	 * @return List of users with matching last name
	 */
	public List<Userdto> getUserByLastName(String lastName) {
		List<Userdto> list = userRepository.findByLastName(lastName);
		if (list.isEmpty()) {
			return list;
		} else {
			return list;
		}
	}

	/**
	 * Retrieves a user by their email
	 * @param email The email to search for
	 * @return User information or error message if user doesn't exist
	 */
	public Object getUserByEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			return userRepository.findByEmail(email);
		} else {
			return "User Does Not Exists";
		}
	}

	/**
	 * Retrieves all users from the system
	 * @return List of all users or message if no users exist
	 */
	public Object fetchAll() {
		List<Userdto> list = userRepository.findAll();
		if (list.isEmpty()) {
			return "No Records Found";
		} else {
			return list;
		}
	}

	/**
	 * Authenticates a user login attempt
	 * @param username The username for login
	 * @param password The password for login
	 * @return ResponseEntity with success message or error message
	 */
	public ResponseEntity<Object> loginUser(String username, String password) {
		String hashPassword = PasswordHashing.hashPassword(password);
		String encrpyUsername = EncryptUsername.encrypt(username);
		if (userRepository.existsByUserName(encrpyUsername)) {
			Userdto userdto = (Userdto) userRepository.findByUserName(encrpyUsername);
			if (userdto.getPassword().equals(hashPassword)) {
				return new ResponseEntity<Object>("USER EXISTS WITH USER ID:" + " " + userdto.getId(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("INCORRECT PASSWORD", HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<Object>(" NO USER FOUND WITH THIS EMAIL", HttpStatus.CONFLICT);
		}
	}

}