package com.emir.securitydemo.service;

import com.emir.securitydemo.model.Employee;
import com.emir.securitydemo.model.EmployeeUserDetails;
import com.emir.securitydemo.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("employeeDetailsService")
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService{
	private final EmployeeRepository employeeRepository;

	public EmployeeDetailsServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employee> employeeOptional = employeeRepository.findByUsername(username);
		if(employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			employee.setRoles(employeeOptional.get().getRoles());
			return new EmployeeUserDetails(employee);
		}
		throw new UsernameNotFoundException("User whose username is " + username + " not found.");
	}
}
