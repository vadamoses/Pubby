package com.vada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vada.models.AppUser;
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

	AppUser findByUsername(String username);

}
