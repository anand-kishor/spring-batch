package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
	@Bean
	public RouteLocator gatewayLocator(RouteLocatorBuilder builder)
	{
		return builder.routes()
				.route(r ->r.path("/employee/**")
				.filters(f->f.addRequestHeader("first-request", "first-request-header").addRequestHeader("first-response","first-response-header"))		
				.uri("http://localhost:8081/").id("employeemodule"))
				.route(r ->r.path("/consumer/**")
				.filters(f->f.addRequestHeader("second-request", "second-request-header").addRequestHeader("second-response","second-response-header"))			
				.uri("http://localhost:8082/").id("consumermodule"))
				.build();
	}
	/*
	 * @Bean public RouteLocator gatewayLocator(RouteLocatorBuilder builder) {
	 * return builder.routes() .route(r
	 * ->r.path("/employee/**").uri("http://localhost:8081/").id("employeemodule"))
	 * .route(r
	 * ->r.path("/consumer/**").uri("http://localhost:8082/").id("consumermodule"))
	 * .build(); }
	 */

}
