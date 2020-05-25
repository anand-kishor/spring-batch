package com.messagingrabbit;

//import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MarshallingMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.messagingrabbit.orders.Orders;

@EnableBinding({Source.class,Orders.class})
@SpringBootApplication
public class PriyaSpringCloudStreamRabbitPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriyaSpringCloudStreamRabbitPublisherApplication.class, args);
	}
 @StreamListener("payments") public void receviveEmployee(Employee emp) {
  System.out.println(emp);
  } 
 
	/*
	 * @Bean
	 * 
	 * @StreamMessageConverter public MarshallingMessageConverter
	 * getCustomConverter() { Jaxb2Marshaller marshaller=new Jaxb2Marshaller();
	 * marshaller.setClassesToBeBound(Employee.class);
	 * marshaller.setSupportJaxbElementClass(true); return new
	 * MarshallingMessageConverter(marshaller); }
	 */
}