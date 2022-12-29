package com.museum.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.museum.model.User;
import com.museum.repository.UserRepository;



@Controller
public class ChangePasswordController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/changePassword")
	public String Password() {
		
	
		return "changePassword";
	}
	
	@PostMapping("/ChangePassword")
	public String ChangePassword(@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword,Principal principal, RedirectAttributes redirectAttributes) {
		String userName = principal.getName();
		User user = this.userRepository.findByUsername(userName);
		
		
		System.out.println("OLD PASSWORD"+ oldPassword);
		System.out.println("NEW PASSWORD"+ newPassword);
		System.out.println(user.getPassword());
		
		if(this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())){
			user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(user);
			 redirectAttributes.addFlashAttribute("message", "Password has successfully changed..");
			 redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}else {
			 redirectAttributes.addFlashAttribute("message", "Wrong old password please enter correct old password..");
			 redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return "redirect:/changePassword";
	}
	
}
