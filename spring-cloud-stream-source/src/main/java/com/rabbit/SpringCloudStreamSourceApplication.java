package com.rabbit;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;

@SpringBootApplication
@EnableBinding(Source.class)
public class SpringCloudStreamSourceApplication {
	
	@InboundChannelAdapter(value=Source.OUTPUT,poller=@Poller(fixedDelay="1000",maxMessagesPerPoll = "1"))
    public MessageSource<Long> timeSendMessage()
    {
		return ()->MessageBuilder.withPayload(new Date().getTime()).build();
		
    }
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamSourceApplication.class, args);
	}

}
