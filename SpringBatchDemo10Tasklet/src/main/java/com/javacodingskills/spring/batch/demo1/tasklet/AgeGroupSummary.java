package com.javacodingskills.spring.batch.demo1.tasklet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.util.CollectionUtils;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.utils.Constants;

public class AgeGroupSummary implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		try(Stream<String> employees=Files.lines(Paths.get("/SpringBatchDemo10Tasklet/src/main/resources/employees.csv")))
		{
			List<EmployeeDTO> employeeDTOList=employees.map((strData)->strData.split(","))
				.map(AgeGroupSummary::employeeMapper)	
				.collect(Collectors.toList());
			
			if(CollectionUtils.isEmpty(employeeDTOList))
			{
				Map<String, Long> employeeTo=employeeDTOList.stream().collect(Collectors
						.groupingBy(EmployeeDTO::getAge,Collectors.counting()));
				System.out.println(employeeTo);
			}
		}
		return RepeatStatus.FINISHED;
	}
	public static EmployeeDTO employeeMapper(String[] record )
	{
		EmployeeDTO employeeDTO=new EmployeeDTO();
		employeeDTO.setEmployeeId(record[0]);
		employeeDTO.setFirstName(record[1]);
		employeeDTO.setLastName(record[2]);
		employeeDTO.setEmail(record[3]);
		employeeDTO.setAge(record[4]);
		return employeeDTO;
	}

}
