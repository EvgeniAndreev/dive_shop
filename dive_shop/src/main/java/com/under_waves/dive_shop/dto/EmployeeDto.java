package com.under_waves.dive_shop.dto;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeDto {

    private Long id;

    private String fullName;

    private String phone;

    private String ucn;

    private String email;

    private String password;

    private Set<RoleDto> roles;
}
