package com.emp1.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

public class EmpDTO {
	
	private int id;
	private String name;
	private String address;
	private String deptname;
	private String imageUrl;
	
	
	

	public EmpDTO(int id, String name, String address, String deptname, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.deptname = deptname;
		this.imageUrl = imageUrl;
	}
	public int getId() {
		return id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	@Override
	public String toString() {
		return "EmpDTO [ name=" + name + ", address=" + address + ", deptname=" + deptname + "]";
	}
	
	


}
