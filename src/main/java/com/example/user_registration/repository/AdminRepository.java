package com.example.user_registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_registration.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	Admin findByName(String username);
}
