package com.example.user_registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_registration.dto.Userdto;

public interface UserRepository extends JpaRepository<Userdto, Integer> {

	boolean existsByUserName(String userName);

	Object findByUserName(String userName);
	
	List<Userdto> findByFirstName(String firstName);

	List<Userdto> findByLastName(String lastName);
	
	boolean existsByEmail(String email);

	Object findByEmail(String email);

	boolean existsByPassword(String password);



}
