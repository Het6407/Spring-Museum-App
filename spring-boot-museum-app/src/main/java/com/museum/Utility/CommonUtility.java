package com.museum.Utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.museum.model.User;

public class CommonUtility {

	  public static final String BASE_URL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();

	    public static final String MAIL_FROM = "efarmingcontact@gmail.com";
	    public static User getAuthenticatedUser() {
	        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    }
}
