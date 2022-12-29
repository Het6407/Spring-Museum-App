package com.museum.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.museum.model.Profile;


public interface ProfileRepository extends JpaRepository<Profile,Long>{

	
}
