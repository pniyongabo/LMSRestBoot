package com.gcit.lms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	static Logger logger  = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "/")
	public String welcome() {
		logger.debug("home logger DEBUG is working!");
		logger.info("home logger INFO is working!");
		return "Welcome to back end!";
	}
	
	@RequestMapping(value = "/api/hello")
	public String home() {
		return "Welcome to back end!";
	}
}