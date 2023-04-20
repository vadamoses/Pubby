package com.vada.interfaces;

import java.util.List;

import com.vada.models.AppUser;

public interface UserService {
    
    AppUser findUserById(Long id);
    
    List<AppUser> findAllUsers();
    
    void saveUser(AppUser appUser);

	AppUser findByUsername(String username);
    
}
