package com.emir.securitydemo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeUserDetails implements UserDetails {

	private final String name;
	private final String surname;
	private final String username;
	private final String password;
	private final List<GrantedAuthority> authorities;

	public EmployeeUserDetails(Employee employee) {
		 name = employee.getName();
		 surname = employee.getSurname();
		 username = employee.getUsername();
		 password = employee.getPassword();
		 authorities = employee.getRoles().stream().map(i -> new SimpleGrantedAuthority(i.getName())).collect(Collectors.toList());
		 /*authorities = Arrays.stream(employee.getRoles().toArray(new String[0]))
				 .map(SimpleGrantedAuthority :: new)
				 .collect(Collectors.toList());*/
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
