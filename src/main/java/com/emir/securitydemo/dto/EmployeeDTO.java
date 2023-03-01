package com.emir.securitydemo.dto;

import com.emir.securitydemo.model.Role;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String name;
    private String surname;
    private String username;
    private String password;
    private List<Role> roles;
}
