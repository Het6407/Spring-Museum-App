package com.museum.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.museum.model.User;
import com.museum.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService; 
	

	
	
	@GetMapping("/profile")
	public String showProfile(  Model model,Principal principal) {
		
		String userName = principal.getName();
		model.addAttribute("user", userService.findByUsername(userName));
		
		
		return "profile";
	}
	
	public String uploadDirectory=System.getProperty("User.dir")+"/src/main/resources/static/images";
	@RequestMapping(value="/saveProfile", consumes = {"multipart/form-data" })
	public String uploadFile(@PathVariable Long id,@ModelAttribute("uesr") User user,Model model, @RequestPart("file") MultipartFile file) {
		try {
			user.setProfileImage(file.getBytes());
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		file.getOriginalFilename();

		String filename = user.getId()
				+ file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);

		// System.out.println(filename);
		java.nio.file.Path fileNameAndPath = Paths.get(uploadDirectory, filename);

		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {

			e.printStackTrace();
		}

		user.getProfileImage();
		userService.saveUser(user);
		
		return "redirect:/profile";
	}

}
