package com.museum.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.museum.Utility.CommonUtility;
import com.museum.model.Museum;
import com.museum.model.User;
import com.museum.repository.MuseumRepository;

@Service
public class MuseumServiceImpl implements MuseumService {

	@Autowired
	private MuseumRepository museumRepository;

	@Override
	public List<Museum> getAllMuseum() {

		return museumRepository.findAll();
	}

	@Override
	public void saveMuseum(Museum museum) {
		if (museum.getId() != null && museum.getId() != -1L) {
			Museum tblMuseum = museumRepository.findById(museum.getId()).get();
			museum.setDefault_Audio(tblMuseum.getDefault_Audio());
		}
		museumRepository.save(museum);

	}

	@Override
	public Museum getMuseumById(Long id) {
		Optional<Museum> optional = museumRepository.findById(id);

		Museum museum = null;
		if (optional.isPresent()) {
			museum = optional.get();
		} else {
			throw new RuntimeException("User not found for id :: " + id);
		}

		return museum;
	}

	@Override
	public void deleteMuseumById(Long id) {

		this.museumRepository.deleteById(id);

	}

	@Override
	public Museum findById(Long id) {

		return museumRepository.findById(id).get();
	}

	@Override
	public Museum getDefaultAudio() {
		Museum model = null;
		User user = CommonUtility.getAuthenticatedUser();
		System.out.println(user.getId());
		System.out.println(user.getMuseums());
		if (user.getMuseums().getId() != null && user.getMuseums().getId() != -1) {
			model = museumRepository.findMuseumDefaultAudioById(user.getMuseums().getId());
		}
		return model;
	}

	@Override
	public void savedefaultAudio(MultipartFile file) {

		User user = CommonUtility.getAuthenticatedUser();
//		System.out.println(user.getId());
//		System.out.println(user.getMuseums());
		if (user.getMuseums().getId() != null && user.getMuseums().getId() != -1) {
			Museum museum = museumRepository.findById(user.getMuseums().getId()).get();
			if (museum != null) {

				try {
					museum.setDefault_Audio(file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			museumRepository.save(museum);
		}
	}
}
