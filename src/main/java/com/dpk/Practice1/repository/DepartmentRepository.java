package com.dpk.Practice1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpk.Practice1.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	Department findByDeptName(String deptName);

}
