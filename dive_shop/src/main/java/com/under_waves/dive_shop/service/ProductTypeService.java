package com.under_waves.dive_shop.service;


import com.under_waves.dive_shop.dto.ProductTypeDto;

import java.util.Set;

public interface ProductTypeService {

    Set<ProductTypeDto> findAll();

    ProductTypeDto findById(Long id);

    ProductTypeDto findByType(String type);

    ProductTypeDto save(ProductTypeDto productTypeDto);

    ProductTypeDto update(ProductTypeDto productTypeDto);

    void deleteById(Long id);

    void deleteByType(String barCode);
}
