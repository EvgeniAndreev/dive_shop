package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ManufacturerInfoDto;

import java.util.Set;

public interface ManufacturerInfoService {

    Set<ManufacturerInfoDto> findAll();

    ManufacturerInfoDto findById(Long id);

    ManufacturerInfoDto findByPhone(String phone);

    ManufacturerInfoDto findByEmail(String email);

    ManufacturerInfoDto save(ManufacturerInfoDto manufacturerInfoDto);

    ManufacturerInfoDto update(ManufacturerInfoDto manufacturerInfoDto);

    void deleteById(Long id);

    void deleteByPhone(String phone);

    void deleteByEmail(String email);
}
