package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ImageDto;

import java.util.Set;

public interface ImageService {

    Set<ImageDto> findAll();

    ImageDto findById(Long id);

    ImageDto findByName(String name);

    ImageDto save(ImageDto imageDto);

    ImageDto update(ImageDto imageDto);

    void deleteById(Long id);

    void deleteByName(String name);
}
