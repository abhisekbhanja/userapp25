package com.emp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp1.model.Employee;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer>{

}
