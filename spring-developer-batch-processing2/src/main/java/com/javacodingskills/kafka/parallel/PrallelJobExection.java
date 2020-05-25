package com.javacodingskills.kafka.parallel;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import com.javacodingskills.kafka.model.Transaction;
@Configuration
public class PrallelJobExection {
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
	public StaxEventItemReader<Transaction> xmlTransactionReader(@Value("#{jobParameters['inputXmlFile']}") Resource resource)
	{
		Jaxb2Marshaller unmarshaller=new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(Transaction.class);
		
		return new StaxEventItemReaderBuilder<Transaction>()
				.name("xmlFileTransactionReader")
				.resource(resource)
				.addFragmentRootElements("transaction")
				.unmarshaller(unmarshaller)
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
	public Job parallelFlow()
	{
		Flow secondFlow=new FlowBuilder<Flow>("secondFlow")
				.start(step2())
				.build();
		Flow parallelFlow=new FlowBuilder<Flow>("parallelFlow")
				.start(step1())
				.split(new SimpleAsyncTaskExecutor())
				.add(secondFlow)
				.build();
		return this.jobBuilderFactory.get("parallelJob")
				.start(parallelFlow)
				.end()
				.build();
	}
	
	
	/*
	 * @Bean public Job squentialJob() { return
	 * this.jobBuilderFactory.get("sequentialJob") .start(step1()) .next(step2())
	 * .build(); }
	 */
	@Bean
	public Step step1() {
		// TODO Auto-generated method stub
		
		return this.stepBuilderFactory.get("step1")
				.<Transaction,Transaction>chunk(50)
				.reader(flatTransactionReader(null))
				.writer(flatTransactionWriter(null))
				.build();
	}
	
	@Bean
	public Step step2() {
		// TODO Auto-generated method stub
	
		return this.stepBuilderFactory.get("step2")
				.<Transaction,Transaction>chunk(50)
				.reader(xmlTransactionReader(null))
				.writer(flatTransactionWriter(null))
				.build()
				;
	}
	

}
