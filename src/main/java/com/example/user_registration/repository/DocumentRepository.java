package com.example.user_registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user_registration.dto.UserDocuments;

@Repository
public interface DocumentRepository extends JpaRepository<UserDocuments, Integer> {
         
}
