package com.museum.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.museum.model.Beacon;


public interface BeaconService {

	List<Beacon> getAllBeacon();
	
	
	
	Beacon getBeaconById(Long id);
	
	void changeStatus(long id);
	
	void deleteBeaconById(long id);

	Beacon findById(Long id);

	void saveBeacon(Beacon beacon, Long id, MultipartFile[] images);
}
