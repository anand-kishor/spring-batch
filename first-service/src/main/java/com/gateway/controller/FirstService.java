package com.gateway.controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class FirstService {
	@RequestMapping("/message")
	public String getMessage(@RequestHeader("first-request") String header)
	{
		System.out.println("second service implementation "+header);
		return "first service found";
	}

}
