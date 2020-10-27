package com.multi.contacsapp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
	@Id
	@Column(name = "DEPT_ID")
	private String id;
	private String deptName;
	private String location;

	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
	private List<Employee> employees = new ArrayList<Employee>();

	// employees 필드에 대한 setter,getter도 추가해야 함.
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Department(String id, String deptName, String location) {
		super();
		this.id = id;
		this.deptName = deptName;
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}