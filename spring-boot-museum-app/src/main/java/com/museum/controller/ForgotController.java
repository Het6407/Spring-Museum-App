package com.museum.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.museum.model.User;
import com.museum.repository.UserRepository;
import com.museum.service.EmailServiceImpl;

@Controller
public class ForgotController {
	Random random = new Random(1000);

	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	
	@RequestMapping("/forgot")
	public String openEmailForm() {
		return "forgot_email_form";
	}
	 
	
	
	@PostMapping("/sendOtp")
	public String sendOtp(@RequestParam("email")String email, RedirectAttributes redirectAttributes,HttpSession session) {
		
		System.out.println("EMAIL"+ email);
		
		
		
		int otp = random.nextInt(999999);
		
		System.out.println("OTP"+ otp);
		
		
		
		String subject="OTP from Museum App";
		String message=""
				+   "<div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>"                                             
				+   "<div style='margin:50px auto;width:70%;padding:20px 0'>"          
				+   "<div style='border-bottom:1px solid #eee'>"
				+   "<p style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Museum app</p>"
				+   "</div>"
				+   "<p style='font-size:1.1em'>Hi, "
				+ 	"</p>"
				+   "<p>You have request for reset password.Please verify the OTP which is given below.</p>"
				+   "<h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;'>"+otp
				+ 	"</h2>"
				+   "<p style='font-size:0.9em;'>Regards,<br />Your Brand</p>"
				+   "<hr style='border:none;border-top:1px solid #eee' />"
				+   "<div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>"
				+   "<p>Museum App</p>"
				+   "<p>1600 Amphitheatre Parkway</p>"
				+   "<p>California</p>"
				+   "</div>"
				+   "</div>"
				+   "</div>";
		String to=email;
		
		boolean flag = this.emailServiceImpl.sendEmail(subject, message, to);
		
		if(flag) {
			
			session.setAttribute("myotp", otp);
			session.setAttribute("email",email );
			 return "verify_otp";
		}else {
			redirectAttributes.addFlashAttribute("message", "Check your email id..");
			 redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			
			return "redirect:/forgot";
		}
		
	}
	

	
	@PostMapping("/verify_Otp")
	public String verifyOtp(@RequestParam("otp") int otp,HttpSession session) {
		
		int myotp=(int)session.getAttribute("myotp");
		String email=(String)session.getAttribute("email");
		if(myotp==otp) {
			
			User user = this.userRepository.findByEmail(email);
			
			if(user == null) {
				
				session.setAttribute("message", "User does not exists with this email !!");
				return "forgot_email_form";
			}else {
				
			}
			
			return "reset_password_form";
		}else {
			session.setAttribute("message", "You have entered wrong otp!!"); 
			return "verify_otp";
		}
	}
	
	@PostMapping("/change_password")
	public String ChangePassword(@RequestParam("newPassword") String newPassword,HttpSession session) {
		
		String email = (String)session.getAttribute("email");
		User user = this.userRepository.findByEmail(email);
		user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		this.userRepository.save(user);
		
		return "redirect:/?change=password change successfully..";
	}
}
