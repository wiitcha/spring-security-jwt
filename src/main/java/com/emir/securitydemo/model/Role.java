package com.emir.securitydemo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="role")
public class Role {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	public Role() {
		
	}

	public Role(String name) {
		this.name = name;
	}
	
	
	
	
	
	
	
	
	
	
	

}
