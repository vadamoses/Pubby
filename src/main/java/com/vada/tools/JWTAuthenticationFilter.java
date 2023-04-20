package com.vada.tools;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vada.contollers.dto.AppUserDTO;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	static final String LOGIN_URI = "/login";
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	public JWTAuthenticationFilter() {
		setFilterProcessesUrl(LOGIN_URI);
		setAuthenticationSuccessHandler(new JWTAuthenticationSuccessHandler(jwtProperties()));
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() {
		return authentication -> {
			UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
			if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			} else {
				throw new BadCredentialsException("Invalid username or password");
			}
		};
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			AppUserDTO credentials = new ObjectMapper().readValue(request.getInputStream(), AppUserDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					credentials.getUsername(), credentials.getPassword(), Collections.emptyList());
			return this.authenticationManagerBean().authenticate(authenticationToken);
		} catch (IOException e) {
			throw new BadCredentialsException("Invalid username or password");
		}
	}
	
	@Bean
	public JwtProperties jwtProperties() {
		return new JwtProperties();
	}

}
