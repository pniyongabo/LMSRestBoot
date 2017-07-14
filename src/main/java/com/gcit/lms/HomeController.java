package com.gcit.lms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@CrossOrigin
@RestController
public class HomeController {
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "/")
	public String welcome() {
		return "Welcome to back end!";
	}
	
	@RequestMapping(value = "/api/hello")
	public String home() {
		return "Welcome to back end!";
	}
}