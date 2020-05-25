package com.rabbit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
@EnableBinding(Sink.class)
@SpringBootApplication
public class SpringCloudStreamSinkApplication {
	private static final Logger LOGGER=LogManager.getLogger( SpringCloudStreamSinkApplication.class);

	@StreamListener(Sink.INPUT)
	public void loggerSink(String date)
	{
		LOGGER.info("receive"+date);
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamSinkApplication.class, args);
	}

}
