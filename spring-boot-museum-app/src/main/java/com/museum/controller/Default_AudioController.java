package com.museum.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.museum.model.Museum;
import com.museum.service.MuseumService;
import com.museum.service.UserService;

@Controller
public class Default_AudioController {

	@Autowired
	private MuseumService museumService;
	
	
	
//	@GetMapping("/default_Audio")
//	public String default_Audio( Museum museum,Model model) {
//		
//		model.addAttribute("museum",museumService.getDefaultAudio());
//		return "default_Audio";
//	}
	
//	public String uploadDirectory=System.getProperty("User.dir")+"/src/main/resources/static/images";
//	@PostMapping("/uploadAudio/{id}")
//	public String uploadFile(@PathVariable Long id,@ModelAttribute("museum") Museum museum,Model model, @RequestPart("file") MultipartFile file) {
//		try {
//			museum.setDefault_Audio(file.getBytes());
//		} catch (IOException e1) {
//
//			e1.printStackTrace();
//		}
//
//		file.getOriginalFilename();
//
//		String filename = museum.getId()
//				+ file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);
//
//		// System.out.println(filename);
//		java.nio.file.Path fileNameAndPath = Paths.get(uploadDirectory, filename);
//
//		try {
//			Files.write(fileNameAndPath, file.getBytes());
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
//
//		museum.getDefault_Audio();
//		museumService.saveMuseum(museum);
//		
//		return "redirect:/default_Audio";
//	}
}
