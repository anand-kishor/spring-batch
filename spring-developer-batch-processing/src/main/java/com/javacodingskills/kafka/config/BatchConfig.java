package com.javacodingskills.kafka.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.javacodingskills.kafka.model.Transaction;

@Configuration
public class BatchConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	/*
	 * @Value("classpath:/employees.csv") Resource inputResource;
	 * 
	 * @Bean
	 * 
	 * @StepScope Resource inputFileResource(@Value("#{jobParameters[fileName]}")
	 * final String fileName) { return new ClassPathResource(fileName); }
	 */
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
	
	
	@Bean
	public Job multithreadJob()
	{
		return this.jobBuilderFactory.get("multitheadJob")
				.start(step1())
				.build();
	}
	@Bean
	public Step step1() {
		// TODO Auto-generated method stub
		ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(4);
		taskExecutor.setMaxPoolSize(4);
		taskExecutor.afterPropertiesSet();
		return this.stepBuilderFactory.get("step1")
				.<Transaction,Transaction>chunk(50)
				.reader(flatTransactionReader(null))
				.writer(flatTransactionWriter(null))
				.taskExecutor(taskExecutor)
				.build()
				;
	}
	

}
