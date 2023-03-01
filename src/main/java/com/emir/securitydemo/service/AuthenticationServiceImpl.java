package com.emir.securitydemo.service;

import com.emir.securitydemo.model.Employee;
import com.emir.securitydemo.repository.EmployeeRepository;
import com.emir.securitydemo.repository.RoleRepository;
import com.emir.securitydemo.security.auth.AuthenticationRequest;
import com.emir.securitydemo.security.auth.AuthenticationResponse;
import com.emir.securitydemo.security.auth.RegisterRequest;
import com.emir.securitydemo.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final EmployeeDetailsService employeeDetailsService;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Employee.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roleRepository.findByName("ROLE_USER").stream().collect(Collectors.toList()))
                .build();

        employeeRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String authenticate(AuthenticationRequest request) {
        UserDetails user = null;
        if (StringUtils.hasLength(request.getUsername())
                && StringUtils.hasLength(request.getPassword())
        ) {
            user= this.employeeDetailsService.loadUserByUsername(request.getUsername());
        }
        if (user == null || !passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            return null;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return this.jwtService.generateToken(user);
    }
}
