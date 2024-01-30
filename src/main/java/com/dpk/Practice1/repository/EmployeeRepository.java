package com.dpk.Practice1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpk.Practice1.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	

}
