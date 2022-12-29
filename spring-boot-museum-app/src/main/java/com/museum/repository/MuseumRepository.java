package com.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.museum.model.Museum;



public interface MuseumRepository extends JpaRepository<Museum,Long>{

	Museum getMuseumById(long id);

	@Query("SELECT new com.museum.model.Museum(t.id,t.default_Audio) FROM Museum t WHERE t.id = :id")
	Museum findMuseumDefaultAudioById(Long id);

	

	

}
