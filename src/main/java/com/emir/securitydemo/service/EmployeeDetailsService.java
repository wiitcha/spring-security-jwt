package com.emir.securitydemo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeDetailsService extends UserDetailsService {
}
