package com.museum.service;

import java.util.List;

import com.museum.model.Profile;

public interface ProfileService {

	List<Profile> getAllProfile();
	
	void saveProfile(Profile profile);
	
	Profile getProfileById(Long id);
	
	Profile findById(Long id);

}
