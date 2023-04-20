package com.vada.interfaces;

import java.util.List;

import com.vada.models.AppUser;
import com.vada.models.Authority;

public interface AuthorityService {

	void saveAll(List<Authority> authorities);

	void assignRole(AppUser user, String roleName);

}
