 package com.museum.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.security.InvalidParameterException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.museum.model.User;
import com.museum.service.MuseumService;
import com.museum.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	

	
	@Autowired
	private MuseumService museumService;
	
	@GetMapping("/user")
	public String viewUser(Model model) {
		model.addAttribute("listUsers", userService.getAllUsers());

		return "user";
	}
	

	
	@GetMapping("/AddUser")
	public String addUser(User user,Model model) {
		model.addAttribute("listMuseum", museumService.getAllMuseum());
		model.addAttribute("user",user);
		return "add_User";
	}

	public String uploadDirectory=System.getProperty("User.dir")+"/src/main/resources/static/app-assets/images/avatars";
	
	@RequestMapping(value = "/saveUser", consumes = {"multipart/form-data" })
	public String saveUser(@ModelAttribute("uesr") User user,Model model, @RequestPart("file") MultipartFile file) {
		//System.out.println(user);
		try {
			userService.saveUser(user);
		}
		catch(InvalidParameterException e){
			
			model.addAttribute("error", e.getMessage());
			//System.out.println(model.getAttribute("error"));
			model.addAttribute("listMuseum", museumService.getAllMuseum());
			
			model.addAttribute("user",user);
			return "add_User";
		}
		
		try {
			user.setProfileImage(file.getBytes());
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		file.getOriginalFilename();

		String filename = user.getId()
				+ file.getOriginalFilename().substring(file.getOriginalFilename().length());

		// System.out.println(filename);
		java.nio.file.Path fileNameAndPath = Paths.get(uploadDirectory, filename);

		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {

			e.printStackTrace();
		}

		
		user.getProfileImage();
		userService.saveUser(user);
		
		return "redirect:/user";
	}
	
	@GetMapping("/ShowImage/{id}")
    public void showimage(@PathVariable Long id, HttpServletResponse resp)throws IOException {
        resp.setContentType("image/jpeg");
        try {
            User user = userService.findById(id);
            InputStream is = new ByteArrayInputStream(user.getProfileImage());
            IOUtils.copy(is, resp.getOutputStream());
        } catch (Exception e) {
            throw new ExportException("Image is not available");
        }
    }	
	
	
		
	
	@GetMapping("/updateUser/{id}")
	public String updateUser(@PathVariable(value = "id") long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("listMuseum", museumService.getAllMuseum());

		System.out.println(user);
		model.addAttribute("user", user);
		
		return "update_User";
	}
	

	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
	public String deleteThroughId(@PathVariable(value = "id") long id) {

		userService.deleteUserById(id);
		return "redirect:/user";
	}
   
	@RequestMapping(value = "/status/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveStatus(@PathVariable(value = "id") long id
			) {
	
		userService.changeStatus(id);
		return true;
	}
	
	
}
