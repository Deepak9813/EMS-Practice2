package com.dpk.Practice1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpk.Practice1.model.Department;
import com.dpk.Practice1.repository.DepartmentRepository;
import com.dpk.Practice1.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository deptRepo;
	
	@Override
	public void addDept(Department dept) {
		
		deptRepo.save(dept);
		
	}

	@Override
	public void deleteDept(int id) {
		
		deptRepo.deleteById(id);
		
	}

	@Override
	public void updateDept(Department dept) {

		deptRepo.save(dept);
	}

	@Override
	public List<Department> getAllDept() {
		
		return deptRepo.findAll();
	}

	@Override
	public Department getDeptById(int id) {
		
		return deptRepo.findById(id).get();
	}

	@Override
	public Department getDeptByDeptName(String deptName) {
		
		return deptRepo.findByDeptName(deptName);
	}

}
