package com.rabbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.model.Student;
import com.rabbit.source.StudentSource;
@EnableBinding(StudentSource.class)
@RestController
public class StudentController {
	@Autowired
	private StudentSource source;
	@RequestMapping("/student")
	@ResponseBody
	public String student(@RequestBody Student student)
	{
		source.outputStudentChannel().send(MessageBuilder.withPayload(student).build());
		System.out.println(student.toString());
		return "student is submiited in the student channel";
	}

}
