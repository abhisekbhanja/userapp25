package com.emp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empdept")
public class Dept {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deptid;
	private String deptname;
	
	public Dept() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Dept(int deptid, String deptname) {
		super();
		this.deptid = deptid;
		this.deptname = deptname;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	
	
}
