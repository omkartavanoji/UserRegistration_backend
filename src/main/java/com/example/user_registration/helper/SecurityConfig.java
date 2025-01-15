package com.example.user_registration.helper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService service() {
		UserDetails details = User.withUsername("omkar").password(encoder().encode("omkar2531")).build();
		return new InMemoryUserDetailsManager(details);
	}

	@Bean
	SecurityFilterChain chain(HttpSecurity security) throws Exception {
		return security.csrf(x -> x.disable()).cors(Customizer.withDefaults()).httpBasic(Customizer.withDefaults()).formLogin(i -> i.disable())
				.authorizeHttpRequests(i -> i.anyRequest().authenticated()).build();
	}
}
