package com.emp1.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "emp")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String imageUrl;
	private String address;
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "dept_id")
	private Dept dept;
	
	public Employee(int id, String name, String address, Dept dept) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.dept = dept;
	}
	public Employee(int id, String name, String imageUrl, String address, Dept dept) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.address = address;
		this.dept = dept;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(int id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}
	public int getId() {
		return id;
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
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + ", address=" + address + ", dept="
				+ dept + "]";
	}
	
	
	

	
	
	
	
}
