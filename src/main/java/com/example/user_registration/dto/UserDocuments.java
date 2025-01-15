package com.example.user_registration.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Component
@Entity
@Data
public class UserDocuments {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	private String documentUrl;

}

