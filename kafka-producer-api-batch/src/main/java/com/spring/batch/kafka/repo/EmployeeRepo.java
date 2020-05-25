package com.spring.batch.kafka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.batch.kafka.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
	

}
