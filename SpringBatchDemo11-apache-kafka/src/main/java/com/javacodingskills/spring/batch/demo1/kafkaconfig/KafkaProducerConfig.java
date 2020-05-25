package com.javacodingskills.spring.batch.demo1.kafkaconfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.javacodingskills.spring.batch.demo1.model.Employee;

@Configuration
public class KafkaProducerConfig {
	
	@Bean
	public Map<String,Object> producerConfig()
	{
		Map<String,Object> props=new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		props.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, "654321");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return props;
	}
	@Bean
	public ProducerFactory<String,Employee> producerFactory()
	{
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}
	@Bean
	public KafkaTemplate<String,Employee> kafkaTemplate()
	{
		return new KafkaTemplate<>(producerFactory());
	}

}
