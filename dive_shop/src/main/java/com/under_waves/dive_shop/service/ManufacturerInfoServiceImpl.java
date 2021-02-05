package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ManufacturerInfoDto;
import com.under_waves.dive_shop.exception.BadRequestException;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.ManufacturerInfo;
import com.under_waves.dive_shop.repository.ManufacturerInfoRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;
import java.util.stream.Collectors;

public class ManufacturerInfoServiceImpl implements ManufacturerInfoService {

    private final ManufacturerInfoRepository manufacturerInfoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ManufacturerInfoServiceImpl(ManufacturerInfoRepository manufacturerInfoRepository, ModelMapper modelMapper) {
        this.manufacturerInfoRepository = manufacturerInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ManufacturerInfoDto> findAll() {
        return manufacturerInfoRepository.findAll()
                .stream()
                .map(manufacturerInfo -> modelMapper.map(manufacturerInfo, ManufacturerInfoDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public ManufacturerInfoDto findById(@NonNull Long id) {
        ManufacturerInfo manufacturerInfo = manufacturerInfoRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manufacturer with Id: " + id + " does not exists."));
        return modelMapper.map(manufacturerInfo, ManufacturerInfoDto.class);
    }

    @Override
    public ManufacturerInfoDto findByPhone(@NonNull String phone) {
        ManufacturerInfo manufacturerInfo = manufacturerInfoRepository.findByPhone(phone)
                .orElseThrow(() -> new RecordNotFoundException("Manufacturer info with phone: " + phone + " does not exists."));
        return modelMapper.map(manufacturerInfo, ManufacturerInfoDto.class);
    }

    @Override
    public ManufacturerInfoDto findByEmail(@NonNull String email) {
        ManufacturerInfo manufacturerInfo = manufacturerInfoRepository.findByPhone(email)
                .orElseThrow(() -> new RecordNotFoundException("Manufacturer info with email: " + email + " does not exists."));
        return modelMapper.map(manufacturerInfo, ManufacturerInfoDto.class);
    }

    @Override
    public ManufacturerInfoDto save(@NonNull ManufacturerInfoDto manufacturerInfoDto) {
        try {
            manufacturerInfoDto.setId(null);
            ManufacturerInfo manufacturerInfo = modelMapper.map(manufacturerInfoDto, ManufacturerInfo.class);
            manufacturerInfoRepository.save(manufacturerInfo);
            return modelMapper.map(manufacturerInfo, ManufacturerInfoDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRecordException("Manufacturer already exists.");
        }
    }

    @Override
    public ManufacturerInfoDto update(@NonNull ManufacturerInfoDto manufacturerInfoDto) {
        if (manufacturerInfoDto.getId() == null) {
            throw new BadRequestException("Id must not be null.");
        }
        ManufacturerInfo manufacturerInfo = modelMapper.map(manufacturerInfoDto, ManufacturerInfo.class);
        ManufacturerInfo savedManufacturerInfo = manufacturerInfoRepository.save(manufacturerInfo);
        return modelMapper.map(savedManufacturerInfo, ManufacturerInfoDto.class);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        try {
            manufacturerInfoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RecordNotFoundException("Manufacturer info with Id: " + id + " does not exists.");
        }
    }

    @Override
    public void deleteByPhone(@NonNull String phone) {
        try {
            manufacturerInfoRepository.deleteByPhone(phone);
        } catch (Exception e) {
            throw new RecordNotFoundException("Manufacturer with phone: " + phone + " does not exists.");
        }
    }

    @Override
    public void deleteByEmail(@NonNull String email) {
        try {
            manufacturerInfoRepository.deleteByEmail(email);
        } catch (Exception e) {
            throw new RecordNotFoundException("Manufacturer with email: " + email + " does not exists.");
        }
    }
}
