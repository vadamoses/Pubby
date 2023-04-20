package com.vada.interfaces.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vada.interfaces.UserService;
import com.vada.models.AppUser;
import com.vada.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AppUser findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @Override
    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

	@Override
	public AppUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<AppUser> findAllUsers() {
		return userRepository.findAll();
	}

}
