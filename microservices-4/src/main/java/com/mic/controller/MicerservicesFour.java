package com.mic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

@RestController
public class MicerservicesFour {
	private static final Logger LOGGER=LogManager.getLogger(MicerservicesFour.class);

	/*
	 * @Autowired private RestTemplate template;
	 * 
	 * @Bean public RestTemplate template() { return new RestTemplate(); }
	 */
	@Bean 
	public Sampler defaultSampler()
	{
		return Sampler.ALWAYS_SAMPLE;
	}
	@RequestMapping("/microservices4")
	public String getMessage()
	{
		LOGGER.info("inside microservices four");
		/*
		 * String url="http://localhost:8083/microservices"; String
		 * response=(String)template.exchange(url, HttpMethod.GET, null,
		 * String.class).getBody();
		 */
		return "Hello anand";
	}

}
