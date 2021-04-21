package com.under_waves.dive_shop.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Long id;

    private String fullName;

    private String phone;

    private String email;

    private String password;
}
