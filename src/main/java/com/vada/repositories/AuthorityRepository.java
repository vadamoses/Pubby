package com.vada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vada.models.AppUser;
import com.vada.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByUser(AppUser user);
}
