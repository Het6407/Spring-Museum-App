package com.museum.service;

import java.util.List;

import com.museum.model.Audio_file;

public interface Audio_fileService {

	List<Audio_file> getAllAudio_file();
	
	void saveAudio_file(Audio_file audio_file);

	void ChangeStatus(long id);
	
	void deleteAudio_fileById(long id);
	
	Audio_file getAudio_fileById(Long id);

	Audio_file findById(Long id);

	

	
}
