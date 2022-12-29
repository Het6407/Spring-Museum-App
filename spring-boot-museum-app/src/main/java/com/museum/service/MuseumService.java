package com.museum.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.museum.model.Museum;

public interface MuseumService {

	List<Museum> getAllMuseum();

	void saveMuseum(Museum museum);

	void savedefaultAudio(MultipartFile file);

	Museum getMuseumById(Long id);

	void deleteMuseumById(Long id);

	Museum findById(Long id);

	Museum getDefaultAudio();

}
