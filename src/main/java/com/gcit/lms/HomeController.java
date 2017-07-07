package com.gcit.lms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "welcome";
	}
}