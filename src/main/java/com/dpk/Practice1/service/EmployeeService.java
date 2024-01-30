package com.dpk.Practice1.service;

import java.util.List;

import com.dpk.Practice1.model.Employee;

public interface EmployeeService {

	void addEmp(Employee emp);

	void deleteEmp(Long id);

	void updateEmp(Employee emp);

	List<Employee> getAllEmp();

	Employee getEmpById(Long id);

}
