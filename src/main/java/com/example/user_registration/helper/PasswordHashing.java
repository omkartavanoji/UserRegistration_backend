package com.example.user_registration.helper;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class PasswordHashing {

	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 256;
	 

	public static String hashPassword(String password) {
		String salt="12345";
		 try { 
	            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
	            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	            byte[] hash = factory.generateSecret(spec).getEncoded();
	            return Base64.getEncoder().encodeToString(hash);
	        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	            System.out.println("Error while hashing password: " + e.getMessage());
	            return null;
	        }
	}
	
	
}
