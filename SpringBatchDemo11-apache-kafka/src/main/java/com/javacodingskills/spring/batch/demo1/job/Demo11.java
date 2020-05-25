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

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.job.kafka.EmployeeKafkaSender;
import com.javacodingskills.spring.batch.demo1.mapper.EmployeeFileRowMapper;
import com.javacodingskills.spring.batch.demo1.model.Employee;
import com.javacodingskills.spring.batch.demo1.processor.EmployeeProcessor;
import com.javacodingskills.spring.batch.demo1.repo.EmployeeRepo;
import com.javacodingskills.spring.batch.demo1.tasklet.AgeGroupSummary;
import com.javacodingskills.spring.batch.demo1.tasklet.DataCleanup;

@Configuration
public class Demo11 {
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private EmployeeProcessor employeeProcessor;
	@Value("classpath:/employees.csv")
	private Resource inputResource;
	
	 
	
	/*
	 * @Autowired public void Demo1(JobBuilderFactory jobBuilderFactory,
	 * StepBuilderFactory stepBuilserFactory, EmployeeProcessor employeeProcessor,
	 * DataSource dataSource) {
	 * 
	 * this.jobBuilderFactory = jobBuilderFactory; this.stepBuilderFactory =
	 * stepBuilserFactory; this.employeeProcessor = employeeProcessor;
	 * this.dataSource = dataSource; }
	 */
	 
	@Qualifier(value="demo11")
	@Bean Job demoJob()
	{
		return this.jobBuilderFactory.get("demo11").start(stepDemo1()).build();
	}
	@Bean
	public Step stepDemo1() {
		// TODO Auto-generated method stub
		return this.stepBuilderFactory.get("step1")
				.<EmployeeDTO,Employee>chunk(5)
				.reader(employeeReader())
				.processor(employeeProcessor)
				.writer(employeeKafkaSender())
				.build();
	}
	
	  
	@Bean public EmployeeProcessor employeeProcessor() { return new
	  EmployeeProcessor(); }
	  
	  @Bean
	   @StepScope 
	   Resource inputFileResource(@Value("#{jobParameters[fileName]}")final String fileName) 
	  {
		  return new ClassPathResource(fileName);
		  }
	  
	
	 
	  @Bean
	  @StepScope public FlatFileItemReader<EmployeeDTO> employeeReader() {
	   FlatFileItemReader<EmployeeDTO> reader=new
	  FlatFileItemReader<EmployeeDTO>(); reader.setResource(inputResource);
	  reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>()
	  {{setLineTokenizer(new DelimitedLineTokenizer()
	  {{setNames("employeeId","firstName","lastName","email","age"); }});
	  setFieldSetMapper(new EmployeeFileRowMapper()); }}); return reader; 
	  }
	  @Bean
	  public EmployeeKafkaSender employeeKafkaSender() {
			// TODO Auto-generated method stub
			return new EmployeeKafkaSender();
		}
	  
	  /*
		 * @Bean public JdbcBatchItemWriter<Employee> employeeWriter() {
		 * JdbcBatchItemWriter<Employee> writer=new JdbcBatchItemWriter<Employee>();
		 * writer.setDataSource(dataSource); writer.
		 * setSql("insert into employee(employee_id,first_name,last_name,email,age) values(:employeeId,:firstName,:lastName,:email,:age)"
		 * ); writer.setItemSqlParameterSourceProvider(new
		 * BeanPropertyItemSqlParameterSourceProvider<Employee>()); return writer; }
		 */
}
