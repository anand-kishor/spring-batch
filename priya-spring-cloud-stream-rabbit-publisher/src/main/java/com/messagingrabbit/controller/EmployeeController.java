package com.messagingrabbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.messagingrabbit.Employee;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private Source source;
	@Autowired
	@Qualifier("orders")
	private MessageChannel orders;
	//@Autowired
	//private SubscribableChannel payments;
	
	//@RequestMapping("/register")
	@PostMapping
	public String registerEmployee(@RequestBody Employee employee)
	{
		source.output().send(MessageBuilder.withPayload(employee).setHeader("x-correlationId", "123456").build());
		
		return "register employee";
	}
	@PostMapping("/orders")
	public String registerEmployeeOrder(@RequestBody Employee employee)
	{
		source.output().send(MessageBuilder.withPayload(employee).setHeader("x-correlationId", "999999").build());
		
		return "register employee orders";
	}
	@PostMapping("/str")
	public String stringCoverter(@RequestBody Employee employee)
	{
		source.output().send(MessageBuilder.withPayload(employee).setHeader("contentType", "text/plain").build());
		
		return "mplyee in string formate";
	}
	

}
