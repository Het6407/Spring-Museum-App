package com.museum.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.museum.model.Audio_file;
import com.museum.model.Beacon;
import com.museum.repository.Audio_fileRepository;
import com.museum.repository.BeaconRepository;

@Service
public class Audio_fileServiceImpl implements Audio_fileService{

	@Autowired
	private Audio_fileRepository audio_fileRepository;
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Autowired BeaconService beaconService;
	
	@Override
	public List<Audio_file> getAllAudio_file() {
		
		return audio_fileRepository.findAll();
	}

	@Override
	public void saveAudio_file(Audio_file audio_file) {
	
		  	audio_file.setStatus(true);
		  	
	            audio_fileRepository.save(audio_file);
		
	        
	        }

	@Override
	public void deleteAudio_fileById(long id) {
		this.audio_fileRepository.deleteById(id);
		
	}

	@Override
	public Audio_file getAudio_fileById(Long id) {
		Optional<Audio_file> optional = audio_fileRepository.findById(id);

		Audio_file audio_file = null;
		if (optional.isPresent()) {
			audio_file = optional.get();
		} else {
			throw new RuntimeException("User not found for id :: " + id);
		}

		return audio_file;
	}

	@Override
	public void ChangeStatus(long id) {
		Audio_file audio_file = audio_fileRepository.findById(id).orElse(null);
		
		System.out.println(audio_file.getStatus());
		if (audio_file.getStatus()) {
			//System.out.println(beacon.getStatus());
			audio_file.setStatus(false);
			audio_fileRepository.save(audio_file);
		} else {
			audio_file.setStatus(true);
			audio_fileRepository.save(audio_file);
		}
		
	}

	@Override
	public Audio_file findById(Long id) {
		return audio_fileRepository.findById(id).get();
	}     
		
	

}
