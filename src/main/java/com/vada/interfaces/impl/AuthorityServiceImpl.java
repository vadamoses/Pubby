package com.vada.interfaces.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vada.interfaces.AuthorityService;
import com.vada.models.AppUser;
import com.vada.models.Authority;
import com.vada.repositories.AuthorityRepository;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public void saveAll(List<Authority> authorities) {
		this.authorityRepository.saveAll(authorities);
	}

	@Override
	public void assignRole(AppUser user, String roleName) {
		Authority authority = new Authority();
		authority.setAuthorityName(roleName);
		authority.setUser(user);
		this.authorityRepository.save(authority);
	}

}
