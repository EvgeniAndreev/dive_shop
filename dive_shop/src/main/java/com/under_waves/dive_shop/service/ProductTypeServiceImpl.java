package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ProductTypeDto;
import com.under_waves.dive_shop.exception.BadRequestException;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.ProductType;
import com.under_waves.dive_shop.repository.ProductTypeRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository, ModelMapper modelMapper) {
        this.productTypeRepository = productTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ProductTypeDto> findAll() {
        return productTypeRepository.findAll()
                .stream()
                .map(productType -> modelMapper.map(productType, ProductTypeDto.class))
                .collect(Collectors.toSet());

    }

    @Override
    public ProductTypeDto findById(@NonNull Long id) {
        ProductType productType = productTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product type with ID: " + id + "does not exists."));
        return modelMapper.map(productType, ProductTypeDto.class);
    }

    @Override
    public ProductTypeDto findByType(@NonNull String type) {
        ProductType productType = productTypeRepository.findByType(type)
                .orElseThrow(() -> new RecordNotFoundException("Product with type: " + type + "does not exists."));
        return modelMapper.map(productType, ProductTypeDto.class);
    }

    @Override
    public ProductTypeDto save(@NonNull ProductTypeDto productTypeDto) {
        try {
            productTypeDto.setId(null);
            ProductType productType = modelMapper.map(productTypeDto, ProductType.class);
            ProductType savedType = productTypeRepository.save(productType);
            return modelMapper.map(savedType, ProductTypeDto.class);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateRecordException("Product already exists.");
        }
    }

    @Override
    public ProductTypeDto update(@NonNull ProductTypeDto productTypeDto) {
        if (productTypeDto.getId() == null) {
            throw new BadRequestException("Id must not be null.");
        }
        ProductType productType = modelMapper.map(productTypeDto, ProductType.class);
        ProductType savedProductType = productTypeRepository.save(productType);
        return modelMapper.map(savedProductType, ProductTypeDto.class);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        try {
            productTypeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RecordNotFoundException("A product with ID: " + id + " already exists.");
        }
    }

    @Override
    public void deleteByType(@NonNull String type) {
        try {
            productTypeRepository.deleteByType(type);
        } catch (Exception e) {
            throw new RecordNotFoundException("A product with Barcode: " + type + " already exists.");
        }
    }
}
