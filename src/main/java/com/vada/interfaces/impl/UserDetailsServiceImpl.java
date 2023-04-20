package com.vada.interfaces.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vada.models.AppUser;
import com.vada.models.Authority;
import com.vada.repositories.AuthorityRepository;
import com.vada.repositories.UserRepository;


@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        Authority authority = authorityRepository.findByUser(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(
            user.getUsername(), user.getPassword(), getAuthorities(authority));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Authority authority) {
    	List<GrantedAuthority> authorities = new ArrayList<>();
       
        authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
            
        return authorities;
    }
}
