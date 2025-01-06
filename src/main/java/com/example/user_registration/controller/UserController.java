package com.example.user_registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.user_registration.dto.Userdto;
import com.example.user_registration.service.UserService;

import jakarta.validation.Valid;

/**
 * REST Controller for handling user-related operations
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * Adds a new user to the system
	 * @param userdto The user data transfer object containing user information
	 * @param bindingResult Contains the result of the validation
	 * @return ResponseEntity containing the result of the operation
	 */
	@PostMapping
	public ResponseEntity<Object> addUser(@Valid @RequestBody Userdto userdto, BindingResult bindingResult) {
		return userService.addUser(userdto, bindingResult);
	}

	/**
	 * Retrieves a user by their ID
	 * @param id The ID of the user to retrieve
	 * @return The user object if found
	 */
	@GetMapping("/id")
	public Object getUserById(@RequestParam int id) {
		return userService.getUserById(id);
	}

	/**
	 * Retrieves a user by their username
	 * @param userName The username to search for
	 * @return The user object if found
	 */
	@GetMapping("/username")
	public Object getUserByName(@RequestParam String userName) {
		return userService.getUserByName(userName);
	}

	/**
	 * Retrieves users by their first name
	 * @param firstName The first name to search for
	 * @return List of users with matching first name
	 */
	@GetMapping("/firstname")
	public List<Userdto> getUserByFirstName(@RequestParam String firstName) {
		return userService.getUserByFirstName(firstName);
	}

	/**
	 * Retrieves users by their last name
	 * @param lastName The last name to search for
	 * @return List of users with matching last name
	 */
	@GetMapping("/lastname")
	public List<Userdto> getUserByLastName(@RequestParam String lastName) {
		return userService.getUserByLastName(lastName);
	}

	/**
	 * Retrieves a user by their email address
	 * @param email The email address to search for
	 * @return The user object if found
	 */
	@GetMapping("/email")
	public Object getUserByEmail(@RequestParam String email) {
		return userService.getUserByEmail(email);
	}

	/**
	 * Retrieves all users from the system
	 * @return List of all users
	 */
	@GetMapping()
	public Object fetchAll() {
		return userService.fetchAll();
	}

	/**
	 * Authenticates a user
	 * @param username The username for login
	 * @param password The password for login
	 * @return ResponseEntity containing the result of the authentication
	 */
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password) {
		return userService.loginUser(username, password);
	}

}