package com.javacodingskills.kafka.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.javacodingskills.kafka.model.Employee;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	public KafkaConsumerConfig() {
		
		// TODO Auto-generated constructor stub
	}
	@Bean
	public ConsumerFactory<String,Employee> consumerFactory()
	{
		JsonDeserializer<Employee> deserializer=new JsonDeserializer<>(Employee.class);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),deserializer);
		
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,Employee> KafkaListenerContainerFactory()
	{
		ConcurrentKafkaListenerContainerFactory<String,Employee>  factory=new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	@Bean
	public Map<String,Object>  consumerConfigs() {
		// TODO Auto-generated method stub
		JsonDeserializer<Employee> deserializer=new JsonDeserializer<>(Employee.class);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		Map<String,Object> props=new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "boot");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "123456");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,deserializer);
		props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "2000");
		return props;
	}
	

}
