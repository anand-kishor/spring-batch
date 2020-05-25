package com.javacodingskills.spring.batch.demo1.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;

import com.javacodingskills.spring.batch.demo1.mapper.EmployeeFileRowMapper;
import com.javacodingskills.spring.batch.demo1.model.Employee;
import com.javacodingskills.spring.batch.demo1.processor.EmployeeProcessor;

@Configuration
public class Demo5 {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilserFactory;
	@Autowired
	private EmployeeProcessor employeeProcessor;
	@Autowired
	private DataSource dataSource;
	@Value("classpath:/employees.csv")
	private Resource inputResource;
	private Resource outputResource=new FileSystemResource("output/employee_output.csv");
	 
	/*
	 * @Autowired public Demo1(JobBuilderFactory jobBuilderFactory,
	 * StepBuilderFactory stepBuilserFactory, EmployeeProcessor employeeProcessor,
	 * DataSource dataSource) {
	 * 
	 * this.jobBuilderFactory = jobBuilderFactory; this.stepBuilserFactory =
	 * stepBuilserFactory; this.employeeProcessor = employeeProcessor;
	 * this.dataSource = dataSource; }
	 */
	@Qualifier(value="demo5")
	@Bean Job demoJob()
	{
		return this.jobBuilderFactory.get("demo5")
				.start(stepDemo1())
				.next(stepDemo2())
				.build();
	}
	@Bean
	public Step stepDemo1() {
		// TODO Auto-generated method stub
		return this.stepBuilserFactory.get("step1").<EmployeeDTO,Employee>chunk(5)
				.reader(employeeReader())
				.processor(new EmployeeProcessor())
				.writer(employeeWriter()).build();
	}
	@Bean
	public Step stepDemo2() {
		// TODO Auto-generated method stub
		return this.stepBuilserFactory.get("step2").<Employee,EmployeeDTO>chunk(5)
				.reader(employeeDBReader())
				.writer(emailSenderWriter())
				//.writer(employeeFileWriter())
				.build();
	}
	
	@Bean
	public EmployeeProcessor employeeProcessor()
	{
		return new EmployeeProcessor();
	}
	@Bean
	@StepScope
	Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName)
	{
		return new ClassPathResource(fileName);
	}
	@Bean
	public JdbcBatchItemWriter<Employee> employeeWriter() {
		// TODO Auto-generated method stub
		JdbcBatchItemWriter<Employee> writer=new JdbcBatchItemWriter<Employee>();
		writer.setDataSource(dataSource);
		writer.setSql("insert into employee(employee_id,first_name,last_name,email,age) values(:employeeId,:firstName,:lastName,:email,:age)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return writer;
	}
	@Bean
	@StepScope
	public FlatFileItemReader<EmployeeDTO> employeeReader() {
		// TODO Auto-generated method stub
		FlatFileItemReader<EmployeeDTO> reader=new FlatFileItemReader<EmployeeDTO>();
		reader.setResource(inputResource);
		reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() 
		{{setLineTokenizer(new DelimitedLineTokenizer()
		{{setNames("employeeId","firstName","lastName","email","age");
		}});
		setFieldSetMapper(new EmployeeFileRowMapper());
		}});
		return reader;
	}
	@Bean
	public ItemStreamReader<Employee> employeeDBReader()
	{
		JdbcCursorItemReader<Employee> reader=new JdbcCursorItemReader<Employee>();
		reader.setDataSource(dataSource);
		reader.setSql("select * from employee");
		reader.setRowMapper(new EmployeeRowMapper());
		return reader;
	}

	@Bean
	public ItemWriter<EmployeeDTO> employeeFileWriter() {
		// TODO Auto-generated method stub
		FlatFileItemWriter<EmployeeDTO> writer=new FlatFileItemWriter<>();
		writer.setResource(outputResource);
		writer.setLineAggregator(new DelimitedLineAggregator<EmployeeDTO>(){
				{
					setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDTO>(){{
			setNames(new String[] {"employeeId","firstName","lastName","email","age"});
				}}
				);
				
	}});
	writer.setShouldDeleteIfExists(true);
	return writer;
	}	
	
	@Bean
	public EmailSenderWriter emailSenderWriter()
	{
		return new EmailSenderWriter();
	}
	

}
