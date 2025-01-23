package com.example.user_registration.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	MyUserDetailService userDetailService;

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain chain(HttpSecurity security) throws Exception {
		return security.csrf(x -> x.disable())
				.cors(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.formLogin(i -> i.disable())
				.authorizeHttpRequests(i -> i.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.build();
	}

	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(encoder());
		authenticationProvider.setUserDetailsService(userDetailService);
		return authenticationProvider;
	}
}
