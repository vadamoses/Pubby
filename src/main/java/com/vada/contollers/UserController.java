package com.vada.contollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vada.contollers.dto.AppUserDTO;
import com.vada.contollers.dto.ValidationResponse;
import com.vada.interfaces.AuthorityService;
import com.vada.interfaces.UserService;
import com.vada.models.AppUser;
import com.vada.models.Authority;

@RestController
@CrossOrigin
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	private final AuthorityService authorityService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public UserController(UserService userService,	AuthorityService authorityService) {
		this.userService = userService;
		this.authorityService = authorityService;
	}

	@PostMapping("/register")
	public ResponseEntity<AppUser> register(@RequestBody AppUserDTO requestDTO) {
		LOGGER.info("Start - register user");

		AppUser existingUser = userService.findByUsername(requestDTO.getUsername());
		if (existingUser != null) {
			LOGGER.info("A User with that Username already exists: {}", requestDTO.getUsername());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		AppUser newUser = new AppUser();
		newUser.setUsername(requestDTO.getUsername());
		newUser.setPassword(this.passwordEncoder().encode(requestDTO.getPassword()));
		List<Authority> authorities = new ArrayList<>();
		for (String role : requestDTO.getRoles()) {
			Authority authority = new Authority();
			authority.setAuthorityName(role.toUpperCase(Locale.ROOT));
			authority.setUser(newUser);
			authorities.add(authority);
		}
		userService.saveUser(newUser);
		authorityService.saveAll(authorities);

		LOGGER.info("End - register user");
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	@PostMapping("/login")
	public ResponseEntity<AppUser> login(@RequestBody AppUserDTO loginUser) {
		LOGGER.info("Start - login user");
		AppUser user = userService.findByUsername(loginUser.getUsername());

		if (user == null) {
			LOGGER.info("User not found: {}", loginUser.getUsername());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (this.passwordEncoder().matches(loginUser.getPassword(), user.getPassword())) {
			LOGGER.info("User logged in successfully: {}", user.getUsername());
			return ResponseEntity.ok(user);
		} else {
			LOGGER.info("Incorrect password for user: {}", user.getUsername());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/me")
	public ResponseEntity<AppUser> getLoggedInUser(Authentication authentication) {
		LOGGER.info("Start - get logged in user");
		AppUser user = userService.findByUsername(authentication.getName());
		LOGGER.info("Returning logged in user: {}", user.getUsername());
		return ResponseEntity.ok(user);
	}

	@PostMapping("/assign")
	public ResponseEntity<Authority> assignRole(@RequestParam("userId") Long userId,
			@RequestParam("roleName") String roleName) {
		LOGGER.info("Start - assign role");
		AppUser user = userService.findUserById(userId);
		if (user == null) {
			LOGGER.info("User not found with id: {}", userId);
			return ResponseEntity.notFound().build();
		}
		authorityService.assignRole(user, roleName);
		LOGGER.info("End - assign role");
		return ResponseEntity.ok().build();
	}
	
    public ValidationResponse validateToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getPrincipal();
        AppUser user = this.userService.findByUsername(username);
        ValidationResponse response = new ValidationResponse();

        if (!authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("CUSTOMER")) {
            response.setUserId(user.getId());
        }
        response.setName(user.getUsername());
        return response;
    }
    public List<String> getRoles(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
