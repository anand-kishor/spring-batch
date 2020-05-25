package com.messagingrabbit.orders;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Orders {
	//String OUTPUT="employee-order";
	//String INPUT="employee-payments";
	@Output
	MessageChannel orders();
	@Input
	SubscribableChannel payments();

}
