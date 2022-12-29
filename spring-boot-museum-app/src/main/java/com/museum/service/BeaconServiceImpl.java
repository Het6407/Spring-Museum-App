package com.museum.service;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.museum.model.Beacon;
import com.museum.model.Museum;
import com.museum.model.Image;
import com.museum.repository.BeaconRepository;
import com.museum.repository.ImageRepository;
import com.museum.repository.UserRepository;

@Service
public class BeaconServiceImpl implements BeaconService {

	@Autowired
	private BeaconRepository beaconRepository;

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Beacon> getAllBeacon() {

		return beaconRepository.findAll();
	}

	@Override
	public Beacon getBeaconById(Long id) {
		Optional<Beacon> optional = beaconRepository.findById(id);

		Beacon beacon = null;
		if (optional.isPresent()) {
			beacon = optional.get();
		} else {
			throw new RuntimeException("User not found for id :: " + id);
		}

		return beacon;
	}

	@Override
	public void saveBeacon(Beacon beacon, Long id, MultipartFile[] images) {
		if (id == null || id == -1L) {
            throw new InvalidParameterException("login user Id is null");
        }

        if (beacon.getBeacon_name() == null || beacon.getBeacon_name().equals("")) {
            throw new InvalidParameterException("Beacon name is null");
        }

       
        if(images != null && images.length > 0) {
        	if (Arrays.stream(images).count() > 5) {
            	System.out.println("hi");
                throw new InvalidParameterException("You Can Upload Maximum 5 Images");
            }
        }        

        Museum museum = userRepository.findById(id).get().getMuseums();
        Boolean isUpdate = false;

        if (museum != null) {
            if (beacon.getId() != null) {
                if (beaconRepository.existsByNameForUpdate(beacon.getBeacon_name(), museum.getId(), beacon.getId())) {
                    throw new InvalidParameterException("Beacon with this name already available");
                }
                isUpdate = true;

            } else {
                if (beaconRepository.existsByName(beacon.getBeacon_name(), museum.getId())) {
                    throw new InvalidParameterException("Beacon with this name already available");
                }
            }

            if (!isUpdate) {
                beacon.setStatus(true);
            }else {
            	
            	 beacon.setStatus(beaconRepository.findById(id).get().getStatus());
            }

            beacon.setTblmuseum(museum);
            beaconRepository.save(beacon);
            try {
                if (isUpdate) {
                    List<Image> imagesList = imageRepository.findByTblBeacon(beacon);
                    System.out.println(images != null);
                    if (images == null) {
                        imageRepository.deleteAll(imagesList);
                    }
                }
                if (images != null) {
                    for (MultipartFile image : images) {
                        if (!image.isEmpty() && image.getSize() > 0) {
                            Image tblImages = new Image();
                            tblImages.setData(image.getBytes());
                            tblImages.setFile_name(image.getOriginalFilename());
                            tblImages.setSize(image.getSize());
                            tblImages.setTblBeacon(beacon); 
                            //tblImages.setContentType(image.getContentType());
                            imageRepository.save(tblImages);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidParameterException("You can not add the beacon as there is not museum associated with you!!");
        }
	}

	@Override
	public void changeStatus(long id) {
		Beacon beacon = beaconRepository.findById(id).orElse(null);

		System.out.println(beacon.getStatus());
		if (beacon.getStatus()) {
			//System.out.println(beacon.getStatus());
			beacon.setStatus(false);
			beaconRepository.save(beacon);
		} else {
			beacon.setStatus(true);
			beaconRepository.save(beacon);
		}
	}

	@Override
	public void deleteBeaconById(long id) {
		this.beaconRepository.deleteById(id);

	}

	@Override
	public Beacon findById(Long id) {
		
		return beaconRepository.findById(id).get();
	}

	/*
	 * @Override public void saveBeacon(Beacon beacon) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

//	@Override
//	public void saveBeacon(Beacon beacon) {
//		System.out.println(beacon.getId());
//		if (beacon.getId() == null) {
//			beacon.setStatus(true);
//		}else {
//			beacon.setStatus(beaconRepository.getBeaconStatus(beacon.getId()));
//		}
//		beaconRepository.save(beacon);
//		
//	}

	

}
