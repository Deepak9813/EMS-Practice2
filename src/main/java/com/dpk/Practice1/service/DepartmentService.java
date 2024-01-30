package com.dpk.Practice1.service;

import java.util.List;

import com.dpk.Practice1.model.Department;

public interface DepartmentService {

	void addDept(Department dept);

	void deleteDept(int id);

	void updateDept(Department dept);

	List<Department> getAllDept();

	Department getDeptById(int id);
	
	Department getDeptByDeptName(String deptName);

}
