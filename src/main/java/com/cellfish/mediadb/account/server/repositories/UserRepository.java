package com.cellfish.mediadb.account.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cellfish.mediadb.account.server.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);
	
	User findByEmail(String email);
}
