package com.dpk.Practice1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpk.Practice1.model.Employee;
import com.dpk.Practice1.repository.EmployeeRepository;
import com.dpk.Practice1.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository empRepo;

	@Override
	public void addEmp(Employee emp) {
		
		empRepo.save(emp);
		
	}

	@Override
	public void deleteEmp(Long id) {
		
		empRepo.deleteById(id);
		
	}

	@Override
	public void updateEmp(Employee emp) {
		
		empRepo.save(emp);
		
	}

	@Override
	public List<Employee> getAllEmp() {
		
		return empRepo.findAll();
	}

	@Override
	public Employee getEmpById(Long id) {
		
		return empRepo.findById(id).get();
	}

}
