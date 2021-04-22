package com.under_waves.dive_shop.security;

import com.under_waves.dive_shop.dto.EmployeeDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    String key = "divedivedivedivedivedivedive";

    public String generateToken(EmployeeDto employeeDto, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", employeeDto.getId());
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(employeeDto.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 10000))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String requestTokenHeader = httpServletRequest.getHeader("Authorization");

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            return requestTokenHeader.substring(7);
        } else return "";
    }

    public String getEmailFromToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return body.getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, EmployeeDto employeeDto) {
        String email = getEmailFromToken(token);
        return email.equals(employeeDto.getEmail()) && !isTokenExpired(token);
    }
}