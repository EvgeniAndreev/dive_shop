package com.under_waves.dive_shop.service;


import com.under_waves.dive_shop.dto.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface EmployeeService {

    Set<EmployeeDto> findAll();

    EmployeeDto findById(Long id);

    EmployeeDto findByEmail(String email);

    EmployeeDto findByUcn(String ucn);

    EmployeeDto save(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    void deleteByUcn(String ucn);

    void deleteByEmail(String email);
}
