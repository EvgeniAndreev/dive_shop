package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.EmployeeDto;
import com.under_waves.dive_shop.dto.RoleDto;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Employee;
import com.under_waves.dive_shop.model.Role;
import com.under_waves.dive_shop.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleService roleService, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<EmployeeDto> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public EmployeeDto findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Employee not found. Id: " + id));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto findByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("Employee not found. Email: " + email));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto findByUcn(String ucn) {
        Employee employee = employeeRepository.findByUcn(ucn)
                .orElseThrow(() -> new RecordNotFoundException("Employee not found. Email: " + ucn));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        try {
            employeeDto.setId(null);
            Employee employee = modelMapper.map(employeeDto, Employee.class);
            RoleDto roleEmployee = roleService.findByName("ROLE_EMPLOYEE");
            Role role = modelMapper.map(roleEmployee, Role.class);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            employee.setRoles(roles);
            Employee saved = employeeRepository.save(employee);
            return modelMapper.map(saved, EmployeeDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRecordException("Player with email " + employeeDto.getEmail() + " already registered");
        }
    }

    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public void deleteByUcn(String ucn) {
        try {
            employeeRepository.deleteByUcn(ucn);
        } catch (Exception e) {
            throw new RecordNotFoundException("Employee not found.");
        }
    }

    @Override
    public void deleteByEmail(String email) {
        try {
            employeeRepository.deleteByUcn(email);
        } catch (Exception e) {
            throw new RecordNotFoundException("Employee not found.");
        }
    }
}
