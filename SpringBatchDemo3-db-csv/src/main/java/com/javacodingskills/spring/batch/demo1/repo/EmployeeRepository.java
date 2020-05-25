package com.javacodingskills.spring.batch.demo1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javacodingskills.spring.batch.demo1.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
