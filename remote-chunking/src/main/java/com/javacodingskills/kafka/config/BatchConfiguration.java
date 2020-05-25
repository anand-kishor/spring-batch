package com.javacodingskills.kafka.config;

import javax.sql.DataSource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.integration.chunk.RemoteChunkingMasterStepBuilderFactory;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import com.javacodingskills.kafka.model.Transaction;

@Configuration
public class BatchConfiguration {
	
@Configuration
@Profile("worker")
public static class MasterConfiguration{
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private RemoteChunkingMasterStepBuilderFactory remoteChunkingMasterStepBuilderFactory;
	@Bean
	public DirectChannel requests()
	{
		return new DirectChannel();
	}
	@Bean
	public IntegrationFlow outboundChannel(AmqpTemplate amqpTemplate)
	{
		return IntegrationFlows
				.from(requests())
				.handle(Amqp.outboundAdapter(amqpTemplate).routingKey("requests"))
				.get();
	}
	@Bean 
	public QueueChannel replies()
	{
		return new QueueChannel();
	}
	@Bean
	public IntegrationFlow inboundChannel(ConnectionFactory connectionFactory)
	{
		return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "replies"))
				.channel(replies())
				.get();
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
	public TaskletStep masterStep()
	{
		return this.remoteChunkingMasterStepBuilderFactory.get("masterTaskLetStep")
				.<Transaction,Transaction>chunk(100)
				.reader(flatTransactionReader(null))
				.outputChannel(requests())
				.inputChannel(replies())
				.build();
				
	}
	@Bean
	public Job remoteChunkingJob()
	{
		return this.jobBuilderFactory.get("remotechunkingjob")
				.start(masterStep())
				.build();
	}
	
}
@Configuration
@Profile("worker")
public static class WorkerConfiguration{
	@Autowired
	private RemoteChunkingWorkerBuilder<Transaction,Transaction> workerBuilder;
	@Bean 
	public DirectChannel requests()
	{
		return new DirectChannel();
	}
	@Bean 
	public DirectChannel replies()
	{
		return new DirectChannel();
	}
	@Bean
	public IntegrationFlow inboundChannel(ConnectionFactory connectionFactory)
	{
		return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory,"requests"))
				.channel(requests())
				.get();
	}
	@Bean
	public IntegrationFlow outboundChannel(AmqpTemplate template)
	{
		return IntegrationFlows
				.from(replies())
				.handle(Amqp.outboundAdapter(template).routingKey("replies")).get();
	}
	@Bean
	public IntegrationFlow integrationFlow()
	{
		return this.workerBuilder.itemProcessor(processor())
				.itemWriter(flatTransactionWriter(null))
				.inputChannel(requests())
				.outputChannel(replies())
				.build();
	}
	@Bean
	public ItemProcessor<Transaction, Transaction> processor() {
		
		return transaction ->{System.out.println("processing transaction "+transaction);
		return transaction;
		};
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

}
