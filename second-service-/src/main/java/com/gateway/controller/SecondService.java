package com.gateway.controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class SecondService {
	
	@RequestMapping("/message")
	public String getConsumeMessage(@RequestHeader("second-request") String header)
	{
		System.out.println("second service implement "+header);
		return "second service found";
	}

}
