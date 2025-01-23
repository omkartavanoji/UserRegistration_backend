package com.example.user_registration.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.user_registration.dto.Admin;
import com.example.user_registration.dto.AdminPrincipal;
import com.example.user_registration.repository.AdminRepository;

@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByName(username);
		if (admin == null) {
			System.out.println("No user Found");
			throw new UsernameNotFoundException("User Not Found");
		}
		return new AdminPrincipal(admin);
	}

}
