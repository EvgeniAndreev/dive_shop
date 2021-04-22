package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.RoleDto;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Role;
import com.under_waves.dive_shop.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Role not found. Role: " + id));
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto findByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Role not found" + name));
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        try {
            roleDto.setId(null);
            Role role = modelMapper.map(roleDto, Role.class);
            Role saved = roleRepository.save(role);
            return modelMapper.map(saved, RoleDto.class);
        } catch (Exception e) {
            throw new DuplicateRecordException("Role already exists.");
        }
    }

    @Override
    public RoleDto update(RoleDto roleDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RecordNotFoundException("Role not found.");
        }
    }

    @Override
    public void deleteByName(String name) {

    }
}
