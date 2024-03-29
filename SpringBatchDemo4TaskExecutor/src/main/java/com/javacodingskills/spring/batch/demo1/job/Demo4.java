package com.javacodingskills.spring.batch.demo1.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.mapper.EmployeeFileRowMapper;
import com.javacodingskills.spring.batch.demo1.model.Employee;
import com.javacodingskills.spring.batch.demo1.processor.EmployeeProcessor;

@Configuration
public class Demo4 {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilserFactory;
	@Autowired
	private EmployeeProcessor employeeProcessor;
	@Autowired
	private DataSource dataSource;
	@Value("classpath:/employees.csv")
	Resource inputResource;
	 
	/*
	 * @Autowired public Demo1(JobBuilderFactory jobBuilderFactory,
	 * StepBuilderFactory stepBuilserFactory, EmployeeProcessor employeeProcessor,
	 * DataSource dataSource) {
	 * 
	 * this.jobBuilderFactory = jobBuilderFactory; this.stepBuilserFactory =
	 * stepBuilserFactory; this.employeeProcessor = employeeProcessor;
	 * this.dataSource = dataSource; }
	 */
	@Qualifier(value="demo4")
	@Bean Job demoJob()
	{
		return this.jobBuilderFactory.get("demo4").start(stepDemo1()).build();
	}
	@Bean
	public Step stepDemo1() {
		// TODO Auto-generated method stub
		return this.stepBuilserFactory.get("step1").<EmployeeDTO,Employee>chunk(5).reader(employeeReader())
				.processor(new EmployeeProcessor())
				.writer(employeeWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	@Bean
	public TaskExecutor taskExecutor() {
		// TODO Auto-generated method stub
		SimpleAsyncTaskExecutor taskExecutor=new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(5);
		return taskExecutor;
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
	

}
