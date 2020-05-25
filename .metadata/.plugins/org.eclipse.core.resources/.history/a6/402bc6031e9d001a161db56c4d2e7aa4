package com.javacodingskills.kafka.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.integration.partition.RemotePartitioningMasterStepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;

import com.rabbitmq.client.AMQP;

@Configuration
@Profile("master")
public class MasterConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final RemotePartitioningMasterStepBuilderFactory masterStepBuilderFactory;
	public MasterConfiguration(JobBuilderFactory jobBuilderFactory,
			RemotePartitioningMasterStepBuilderFactory masterStepBuilderFactory) {
		super();
		this.jobBuilderFactory = jobBuilderFactory;
		this.masterStepBuilderFactory = masterStepBuilderFactory;
	}
	@Bean
	public DirectChannel requests()
	{
		return new DirectChannel();
	}
	@Bean
	public IntegrationFlow outboundFlow(AmqpTemplate amqpTemplate)
	{
		return IntegrationFlows.from(requests())
				.handle(Amqp.outboundAdapter(amqpTemplate).routingKey("requests"))
				.get();
	}

	@Bean
	public DirectChannel replies()
	{
		return new DirectChannel();
	}
	@Bean
	public IntegrationFlowBuilder inboundFlow(ConnectionFactory connectionFactory)
	{
		return IntegrationFlows
				.from(Amqp.inboundAdapter(connectionFactory,"replies")
			    .outputChannel(replies())
			    .get());
				
	}
	@Bean
	@StepScope
	public MultiResourcePartitioner partitioner(@Value("#{jobParameters['inputFiles']}") Resource[] resources)

	{
		MultiResourcePartitioner partitioner=new MultiResourcePartitioner();
		partitioner.setKeyName("file");
		partitioner.setResources(resources);
		return partitioner;
	}
	@SuppressWarnings("deprecation")
	@Bean
	public Step masterStep()
	{
		return this.masterStepBuilderFactory.get("masterstep")
				.partitioner("workerStep", partitioner(null))
				.outputChannel(requests())
				.inputChannel(replies())
				.build();
	}
	@Bean
	public Job masterJob()
	{
		return this.jobBuilderFactory.get("remotePartitingJob")
				.start(masterStep())
				.build();
	}
}
