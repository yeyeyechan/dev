package com.multi.contacsapp.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Entity
public class Employee {
	@Id
	@Column(name = "EMP_ID")
	private String id;
	private String empName;
	private String phone;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPT_ID", nullable = false)
	private Department department;

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Employee(String id, String empName, String phone) {
		super();
		this.id = id;
		this.empName = empName;
		this.phone = phone;
		this.department = department;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}