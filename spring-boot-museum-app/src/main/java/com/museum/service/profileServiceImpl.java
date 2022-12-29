package com.museum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.museum.model.Profile;
import com.museum.repository.ProfileRepository;

@Service
public class profileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Override
	public void saveProfile(Profile profile) {
		profileRepository.save(profile);
	}

	@Override
	public List<Profile> getAllProfile() {
		
		return profileRepository.findAll();
	}

	@Override
	public Profile findById(Long id) {
		
		return profileRepository.findById(id).get();
	}

	@Override
	public Profile getProfileById(Long id) {
		Optional<Profile> optional = profileRepository.findById(id);

		Profile profile = null;
		if (optional.isPresent()) {
			profile = optional.get();
		} else {
			throw new RuntimeException("User not found for id :: " + id);
		}

		return profile;
	}

}
