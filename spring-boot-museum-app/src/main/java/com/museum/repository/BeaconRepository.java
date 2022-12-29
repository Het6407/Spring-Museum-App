package com.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.museum.model.Audio_file;
import com.museum.model.Beacon;

@Repository
public interface BeaconRepository extends JpaRepository<Beacon,Long>{

	@Query(value = "select beacon.status from beacon where id = :id", nativeQuery = true)
	Boolean getBeaconStatus(Long id);

	/*
	 * @Query(value =
	 * "select beacon.beacon_name from beacon where beacon.beacon_name = :beacon_name and  museum_id = :id and beacon.id = :id2"
	 * , nativeQuery = true) Boolean existsByNameForUpdate(String beacon_name, Long
	 * id, Long id2);
	 * 
	 * @Query(value =
	 * "select beacon.beacon_name from beacon where beacon.beacon_name = :beacon_name and  museum_id = :id"
	 * , nativeQuery = true) Boolean existsByName(String beacon_name, Long id);
	 */

	 @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Beacon b WHERE b.beacon_name = :name AND b.Tblmuseum.id = :museumId")
	    boolean existsByName(String name,Long museumId);
	 
	 @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Beacon b WHERE b.beacon_name = :name AND b.Tblmuseum.id = :museumId AND b.id != :id")
	    boolean existsByNameForUpdate(String name, Long museumId,Long id);

	

	
		
	 
}
