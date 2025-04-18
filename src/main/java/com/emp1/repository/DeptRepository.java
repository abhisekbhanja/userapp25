package com.emp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp1.model.Dept;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer>{

	public Dept findByDeptname(String deptname);
}
