package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ProductDto;

import java.util.Set;

public interface ProductService {

    Set<ProductDto> findAll();

    ProductDto findById(Long id);

    ProductDto findByBarCode(String barCode);

    ProductDto findByName(String name);

    ProductDto save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    void deleteById(Long id);

    void deleteByBarcode(String barcode);
}
