package com.example.user_registration.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_registration.dto.UserDocuments;
import com.example.user_registration.dto.Userdto;
import com.example.user_registration.repository.DocumentRepository;
import com.example.user_registration.service.UserService;

import jakarta.validation.Valid;

/**
 * REST Controller for handling user-related operations
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	DocumentRepository documentRepository;

	/**
	 * Adds a new user to the system
	 * 
	 * @param userdto       The user data transfer object containing user
	 *                      information
	 * @param bindingResult Contains the result of the validation
	 * @return ResponseEntity containing the result of the operation
	 * @throws IOException
	 */
	@PostMapping
	public ResponseEntity<Object> addUser(@Valid @ModelAttribute Userdto userdto, BindingResult bindingResult)
			throws IOException {
		return userService.addUser(userdto, bindingResult);
	}

	/**
	 * Retrieves a user by their ID
	 * 
	 * @param id The ID of the user to retrieve
	 * @return The user object if found
	 */
	@GetMapping("/id")
	public Object getUserById(@RequestParam int id) {
		return userService.getUserById(id);
	}

	/**
	 * Retrieves a user by their username
	 * 
	 * @param userName The username to search for
	 * @return The user object if found
	 */
	@GetMapping("/username")
	public Object getUserByName(@RequestParam String userName) {
		return userService.getUserByName(userName);
	}

	/**
	 * Retrieves users by their first name
	 * 
	 * @param firstName The first name to search for
	 * @return List of users with matching first name
	 */
	@GetMapping("/firstname")
	public List<Userdto> getUserByFirstName(@RequestParam String firstName) {
		return userService.getUserByFirstName(firstName);
	}

	/**
	 * Retrieves users by their last name
	 * 
	 * @param lastName The last name to search for
	 * @return List of users with matching last name
	 */
	@GetMapping("/lastname")
	public List<Userdto> getUserByLastName(@RequestParam String lastName) {
		return userService.getUserByLastName(lastName);
	}

	/**
	 * Retrieves a user by their email address
	 * 
	 * @param email The email address to search for
	 * @return The user object if found
	 */
	@GetMapping("/email")
	public Object getUserByEmail(@RequestParam String email) {
		return userService.getUserByEmail(email);
	}

	/**
	 * Retrieves all users from the system
	 * 
	 * @return List of all users
	 */
	@GetMapping()
	public Object fetchAll() {
		return userService.fetchAll();
	}

	/**
	 * Authenticates a user
	 * 
	 * @param username The username for login
	 * @param password The password for login
	 * @return ResponseEntity containing the result of the authentication
	 */
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password) {
		return userService.loginUser(username, password);
	}

	@DeleteMapping("{id}")
	public String deleteUserById(@PathVariable int id) {
		return userService.deleteUserById(id);
	}

	@GetMapping("/uploads/documents/{filename}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		try {
			Path filePath = Paths.get("uploads/documents/" + filename);
			Resource resource = new FileSystemResource(filePath);

			if (!resource.exists()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			
			String contentType = Files.probeContentType(filePath);
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"").body(resource);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}