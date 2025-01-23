	package com.example.user_registration.dto;
	
	import jakarta.persistence.Entity;
	import jakarta.persistence.Id;
	import lombok.Data;
	
	@Data
	@Entity
	public class Admin {
		@Id
		String name;
		String password;
	}
