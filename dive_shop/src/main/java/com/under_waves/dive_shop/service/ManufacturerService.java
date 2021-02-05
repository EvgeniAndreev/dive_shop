package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ManufacturerDto;

import java.util.Set;

public interface ManufacturerService {

    Set<ManufacturerDto> findAll();

    ManufacturerDto findById(Long id);

    ManufacturerDto findByName(String name);

    ManufacturerDto save(ManufacturerDto manufacturerDto);

    ManufacturerDto update(ManufacturerDto manufacturerDto);

    void deleteById(Long id);

    void deleteByName(String name);

}
