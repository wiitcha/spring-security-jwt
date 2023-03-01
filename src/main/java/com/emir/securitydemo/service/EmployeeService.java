package com.emir.securitydemo.service;


import com.emir.securitydemo.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService{

	public String addEmployee(Employee emp);
	public List<Employee> findAllEmployee();
	public Optional<Employee> findByEmployeeId(Long id);
}
