package com.museum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class democontroller {
	
	
	@GetMapping("/getdata")
	public String demo() {
		System.err.println("helloooooo");
		return "demo  datattattat";
	}

}
