package com.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.museum.model.Beacon;
import com.museum.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long>{

	List<Image> findByTblBeacon(Beacon beacon);

}
