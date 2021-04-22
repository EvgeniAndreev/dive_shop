package com.under_waves.dive_shop.security;

import com.under_waves.dive_shop.dto.EmployeeDto;
import com.under_waves.dive_shop.service.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityAuthenticationProvider(EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (Strings.isBlank(authentication.getName()) || Objects.isNull(authentication.getCredentials())) {
            throw new BadCredentialsException("Email and password should not be null.");
        }
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        EmployeeDto employeeDto = employeeService.findByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, employeeDto.getPassword())) {
            throw new BadCredentialsException("Email or password are not valid.");
        }
        return new UsernamePasswordAuthenticationToken(employeeDto, employeeDto, employeeDto.getRoles());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
