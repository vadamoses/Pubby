package com.vada.interfaces;

import java.util.List;
import java.util.Optional;

import com.vada.models.Role;
import com.vada.models.User;
import com.vada.models.enums.ERole;

public interface UserService {
    
    User findUserById(Long id);
    
    List<User> findAllUsers();
    
    User saveUser(User user);

	User findByUsername(String username);
	
	Role saveRole(Role role);

	/* returning an optional here allows for the use of .orElseThrow(()) in any calling controller */
	Optional<Role> findByName(ERole name);
	
	void addRoleToUser(ERole name, String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

    
}
