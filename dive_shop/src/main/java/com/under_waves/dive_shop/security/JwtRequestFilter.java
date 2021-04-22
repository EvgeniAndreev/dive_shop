package com.under_waves.dive_shop.security;

import com.under_waves.dive_shop.dto.EmployeeDto;
import com.under_waves.dive_shop.service.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final EmployeeService employeeService;

    @Autowired
    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, EmployeeService employeeService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.employeeService = employeeService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest
            , @NotNull HttpServletResponse httpServletResponse
            , @NotNull FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenUtil.getTokenFromRequest(httpServletRequest);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

        if (Strings.isNotBlank(token)) {
            String emailFromToken = jwtTokenUtil.getEmailFromToken(token);
            if (Strings.isNotBlank(emailFromToken)) {
                EmployeeDto employeeDto = employeeService.findByEmail(emailFromToken);
                usernamePasswordAuthenticationToken = validateToken(token, employeeDto);
            }
        }
    }

    private UsernamePasswordAuthenticationToken validateToken(String token, EmployeeDto employeeDto) {
        if (jwtTokenUtil.validateToken(token, employeeDto)) {
            return new UsernamePasswordAuthenticationToken(employeeDto, employeeDto.getPassword(), employeeDto.getRoles());
        }
        return null;
    }
}
