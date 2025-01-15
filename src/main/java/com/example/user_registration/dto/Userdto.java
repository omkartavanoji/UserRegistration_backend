package com.example.user_registration.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Component
@Data
@Entity
public class Userdto {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	@NotEmpty(message = "Username must not be empty")
	private String userName;
	@NotEmpty(message = "Firstname must not be empty")
	private String firstName;
	@NotEmpty(message = "Lastname must not be empty")
	private String lastName;
	@NotEmpty(message = "Email must Not be Empty")
	@Email
	private String email;
	@NotEmpty
	@Pattern(regexp = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least 8 characters, including upper/lowercase, digits, and special characters.")
	private String password;
	private String imageUrl;
	
	@Transient
	private MultipartFile userImage;
	
	@Transient
	private List<MultipartFile> userDocuments;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<UserDocuments> documents;
}
