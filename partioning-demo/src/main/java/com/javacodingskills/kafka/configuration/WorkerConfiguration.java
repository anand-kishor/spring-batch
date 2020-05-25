package com.javacodingskills.kafka.configuration;

import javax.sql.DataSource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;

import org.springframework.integration.dsl.IntegrationFlows;

import com.javacodingskills.kafka.domain.Transaction;

@Configuration
@Profile("master")
public class WorkerConfiguration {

	private final RemotePartitioningWorkerStepBuilderFactory workerStepBuilderFactory;

	public WorkerConfiguration(RemotePartitioningWorkerStepBuilderFactory workerStepBuilderFactory) {
		
		this.workerStepBuilderFactory = workerStepBuilderFactory;
	}
	@Bean
	public DirectChannel requests()
	{
		return new DirectChannel();
	}
	@Bean
	public IntegrationFlow inboundFlow(ConnectionFactory connectionFactory)
	{
		return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory,"requests"))
				.channel(requests())
				.get();
	}
	@Bean
	public DirectChannel replies()
	{
		return new DirectChannel();
	}
	@Bean
	public IntegrationFlow outbound(AmqpTemplate amqpTemplate)
	{
		return IntegrationFlows
				.from(replies())
				.handle(Amqp.outboundAdapter(amqpTemplate).routingKey("replies"))
				.get();
	}
	@Bean
	public Step workerStep()
	{
		return this.workerStepBuilderFactory.get("workerstep").inputChannel(requests()).outputChannel(replies()).<Transaction,Transaction>chunk(100)
				.reader(flatTransactionReader(null)).processor((ItemProcessor<Transaction,Transaction>) transaction ->{
				System.out.println("processing transaction "+transaction);
				return transaction;
					
				}).writer(flatTransactionWriter(null)).build();
	}
	@Bean
	@StepScope
	public FlatFileItemReader<Transaction> flatTransactionReader(@Value("#{jobParameters['inputFlatFile']}") Resource resource)
	{
		return new FlatFileItemReaderBuilder<Transaction>()
				.name("transactionItemReader")
				.resource(resource)
				.delimited()
				.names(new String[] {"account","amount","timestamp"})
				.fieldSetMapper(fieldSet ->{
					Transaction transaction=new Transaction();
					transaction.setAccount(fieldSet.readString("account"));
					transaction.setAmount(fieldSet.readBigDecimal("amount"));
					transaction.setTimestamp(fieldSet.readDate("timestamp","yyyy-MM-dd HH:mm:ss"));
					return transaction;
					
				})
				.build();
				
	}
	@Bean
	@StepScope
	public JdbcBatchItemWriter<Transaction> flatTransactionWriter(DataSource dataSource)
	{
		return new JdbcBatchItemWriterBuilder<Transaction>()
		.dataSource(dataSource)
		.sql("INSERT INTO TRANSACTION(ACCOUNT,AMOUNT,TIMESTAMP) VALUES(:account,:amount,:timestamp)")
		.beanMapped()
		.build();
		
				
	}
	
	
	
}
