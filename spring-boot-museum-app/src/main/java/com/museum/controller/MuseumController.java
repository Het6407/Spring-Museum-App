package com.museum.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.museum.model.Museum;
import com.museum.model.User;
import com.museum.repository.Audio_fileRepository;
import com.museum.repository.BeaconRepository;
import com.museum.repository.MuseumRepository;
import com.museum.repository.UserRepository;
import com.museum.service.MuseumService;

@Controller
public class MuseumController {

	


	@Autowired
	private MuseumService museumService;
	
	@Autowired
	private MuseumRepository museumRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Autowired
	private Audio_fileRepository audio_fileRepository; 
	
	@GetMapping("/")
	public String login() {
		return "login";
	}

	

	
	@GetMapping("/dashBoard")
	public String viewDashBoard(Model model) {
		
		model.addAttribute("total_count", userRepository.count());
		model.addAttribute("count", museumRepository.count());
		model.addAttribute("Total_Count", beaconRepository.count());
		model.addAttribute("Total_count", audio_fileRepository.count());
		
		return "dashBoard";
	}

	
	
	
	
	@GetMapping("/museum")
	public String viewMuseum(Model model) {
		model.addAttribute("listMuseum", museumService.getAllMuseum());
		return "museum";
	}
	
	public String uploadDirectory=System.getProperty("User.dir")+"/src/main/resources/static/images";
	
	@GetMapping("/default_Audio")
	public String getDefaultAudioPage(Model model,User user) {
		
		model.addAttribute("museum",museumService.getDefaultAudio());
		return "default_Audio";
		
	}
	
	@RequestMapping(value="/saveDefault_Audio", consumes = {"multipart/form-data" })
	public String saveDefaultAudio(@ModelAttribute("museum") Museum museum, @RequestPart("file") MultipartFile file) {
		try {
			museum.setDefault_Audio(file.getBytes());
			
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		file.getOriginalFilename();

		String filename = museum.getId()
				+ file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);

		// System.out.println(filename);
		java.nio.file.Path fileNameAndPath = Paths.get(uploadDirectory, filename);

		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {

			e.printStackTrace();
		}

		museum.getDefault_Audio();
		museumService.savedefaultAudio(file);
		return "redirect:/default_Audio";
	}
	
	@RequestMapping(value = "/saveMuseum", consumes = {"multipart/form-data" })
	public String saveMuseum(@ModelAttribute("museum") Museum museum, @RequestPart("file") MultipartFile file) {
		// System.out.println(museum);
		
		try {
			museum.setIcon(file.getBytes());
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		file.getOriginalFilename();

		String filename = museum.getId()
				+ file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);

		// System.out.println(filename);
		java.nio.file.Path fileNameAndPath = Paths.get(uploadDirectory, filename);

		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {

			e.printStackTrace();
		}

		museum.getIcon();
		
		museumService.saveMuseum(museum);

		return "redirect:/museum";

	}
	
	
	@GetMapping("/showimage/{id}")
    public void showProductImage(@PathVariable Long id, HttpServletResponse resp)throws IOException {
        resp.setContentType("image/jpeg");
        try {
            Museum museum = museumService.findById(id);
            InputStream is = new ByteArrayInputStream(museum.getIcon());
            IOUtils.copy(is, resp.getOutputStream());
        } catch (Exception e) {
            throw new ExportException("Image is not available");
        }
    }
	 
	
	 
	@GetMapping(value = "/addMuseum")
	
	public String addMuseum(Museum museum) {
		
		
		return "add_museum";
	}
	
	@RequestMapping(value = "/deleteMuseum/{id}", method = RequestMethod.GET)
	public String deleteMuseum(@PathVariable(value = "id")long id) {
		museumService.deleteMuseumById(id);
		
		return "redirect:/museum";
	}
	
	@GetMapping("/updateMuseum/{id}") 
	public String updateMuseum(@PathVariable(value = "id") long id, Model model) {
		
		Museum museum = museumService.getMuseumById(id);
		model.addAttribute(museum);
		return "update_Museum";
	}
	

	@PostMapping("/updateMuseum/{id}") 
	public String updateMuseum1(@PathVariable(value = "id") long id, Model model, @RequestPart("file") MultipartFile file) {
		System.out.println("hello");
		Museum museum = museumService.getMuseumById(id);
		model.addAttribute(museum);
		return "update_Museum";
	}
	
	
	
	@GetMapping("/showDefaultAudio/{id}")
	    public void getAudioById(HttpServletResponse response, @PathVariable Long id)
	            throws Exception {
	        try {
	            response.setContentType("audio/mp3");
	            byte[] localMp3 = museumService.getDefaultAudio().getDefault_Audio();
	            response.getOutputStream()
	                    .write(localMp3);
	            System.err.println(localMp3.length);

	        } catch (Exception e) {
	            System.out.println(e.getLocalizedMessage());
	        } finally {
	            response.getOutputStream().close();
	        }
	    }
	
}
