package com.vada.interfaces.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vada.interfaces.UserService;
import com.vada.models.Role;
import com.vada.models.User;
import com.vada.models.enums.ERole;
import com.vada.repositories.RoleRepository;
import com.vada.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		User user = new User();
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			user = userOptional.get();
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Optional<Role> findByName(ERole name) {
		return roleRepository.findByName(name);
	}

	@Override
	public void addRoleToUser(ERole name, String username) {
		Role role = new Role();
		Optional<Role> optional = this.findByName(name);
		if (optional.isPresent()) {
			role = optional.get();
		}
		User user = this.findByUsername(username);
		user.getRoles().add(role);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

}
