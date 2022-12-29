package com.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.museum.model.Museum;
import com.museum.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	
	User findByEmail(String email);

	User findByUsername(String userName);

	User findByProfileImage(Long id);
	

	

	
}
