package com.multi.contacsapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multi.contacsapp.domain.Department;

public interface DepartmentRepository  extends JpaRepository<Department, String> {

}
