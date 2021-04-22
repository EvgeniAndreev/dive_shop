package com.under_waves.dive_shop.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class RoleDto implements GrantedAuthority {

    Long id;

    String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
