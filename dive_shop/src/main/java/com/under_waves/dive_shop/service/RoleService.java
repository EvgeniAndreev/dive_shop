package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.RoleDto;
import com.under_waves.dive_shop.model.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {

    RoleDto findById(Long id);

    RoleDto findByName(String name);

    RoleDto save(RoleDto roleDto);

    RoleDto update(RoleDto roleDto);

    void deleteById(Long id);

    void deleteByName(String name);
}
