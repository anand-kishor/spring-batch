package com.rabbit.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StudentSource {
	
	@Output("studentChannel")
    MessageChannel outputStudentChannel();
	

}
