package com.example.user_registration.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.example.user_registration.dto.UserDocuments;
import com.example.user_registration.dto.Userdto;
import com.example.user_registration.helper.CloudinaryImage;
import com.example.user_registration.helper.PasswordHashing;
import com.example.user_registration.repository.DocumentRepository;
import com.example.user_registration.repository.UserRepository;

/**
 * Service class for handling user-related operations
 */
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DocumentRepository documentRepository;

	@Autowired
	CloudinaryImage cloudinaryImage;

	/**
	 * Registers a new user in the system
	 * 
	 * @param userdto       The user data transfer object containing user
	 *                      information
	 * @param bindingResult Contains the validation results
	 * @return ResponseEntity with success message or validation errors
	 * @throws IOException
	 */

	public ResponseEntity<Object> addUser(Userdto userdto, BindingResult bindingResult) throws IOException {
		Map<String, String> errors = new HashMap<>();

		userdto.setUserName(userdto.getUserName().toLowerCase());
		if (userRepository.existsByUserName(userdto.getUserName())) {
			bindingResult.rejectValue("userName", "error.userName", "User With This Username Already Exists");
		}

		MultipartFile image = userdto.getUserImage();
		if (image == null) {
			bindingResult.rejectValue("userImage", "error.userImage", "Please Upload the File");
		}

		String lowerCaseEmail = userdto.getEmail().toLowerCase();
		userdto.setEmail(lowerCaseEmail);
		if (userRepository.existsByEmail(lowerCaseEmail)) {
			bindingResult.rejectValue("email", "error.email", "User With This Email Already Exists");
		}

		List<MultipartFile> documents = userdto.getUserDocuments();
		if (documents == null || documents.isEmpty()) {
			bindingResult.rejectValue("userDocuments", "error.userDocuments", "Please Upload atleast 1 Document");
		}

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		List<UserDocuments> list = new ArrayList<UserDocuments>();
		for (MultipartFile doc : documents) {
			UserDocuments userDocuments = new UserDocuments();
			String documentURL = saveDocument(doc);
			userDocuments.setDocumentUrl(documentURL);
			documentRepository.save(userDocuments);
			list.add(userDocuments);
		}
		userdto.setDocuments(list);

		
		String hashPassword = PasswordHashing.hashPassword(userdto.getPassword());
		userdto.setPassword(hashPassword);
		userdto.setImageUrl(cloudinaryImage.getUrl(image));
		userRepository.save(userdto);

		return new ResponseEntity<>("User Registered Successfully with USER ID: " + "" + userdto.getId(),
				HttpStatus.CREATED);
	}

	private String saveDocument(MultipartFile document) throws IOException {
		String fileName = UUID.randomUUID().toString() + "_" + document.getOriginalFilename();
		Path path = Paths.get("uploads/documents/" + fileName);
		Files.copy(document.getInputStream(), path);
		System.out.println("File saved at: " + path.toString());
		return "http://localhost:8080/users/uploads/documents/" + fileName;
	}

	/**
	 * Retrieves a user by their ID
	 * 
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
	 * 
	 * @param userName The username to search for
	 * @return User information or error message if user doesn't exist
	 */
	public Object getUserByName(String userName) {
		if (userRepository.existsByUserName(userName)) {
			return userRepository.findByUserName(userName);
		} else {
			return "User Does Not Exists";
		}
	}

	/**
	 * Retrieves users by their first name
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @return List of all users or message if no users exist
	 */
	public Object fetchAll() {
		List<Userdto> list = userRepository.findAll();
		if (list.isEmpty()) {
			return "No Users Found";
		} else {
			return list;
		}
	}

	/**
	 * Authenticates a user login attempt
	 * 
	 * @param username The username for login
	 * @param password The password for login
	 * @return ResponseEntity with success message or error message
	 */
	public ResponseEntity<Object> loginUser(String username, String password) {
		String hashPassword = PasswordHashing.hashPassword(password);
		if (userRepository.existsByUserName(username)) {
			Userdto userdto = (Userdto) userRepository.findByUserName(username);
			if (userdto.getPassword().equals(hashPassword)) {
				return new ResponseEntity<Object>("USER EXISTS WITH USER ID:" + " " + userdto.getId(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("INCORRECT PASSWORD", HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<Object>(" NO USER FOUND WITH THIS EMAIL", HttpStatus.CONFLICT);
		}
	}

	public String deleteUserById(int id) {
		userRepository.deleteById(id);
		return "user Deleted Succesfully";
	}

}