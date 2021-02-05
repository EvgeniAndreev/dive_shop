package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ManufacturerDto;
import com.under_waves.dive_shop.dto.ProductDto;
import com.under_waves.dive_shop.exception.BadRequestException;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Manufacturer;
import com.under_waves.dive_shop.model.Product;
import com.under_waves.dive_shop.repository.ManufacturerRepository;
import com.under_waves.dive_shop.repository.ProductRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ManufacturerDto> findAll() {
        return manufacturerRepository.findAll()
                .stream()
                .map(manufacturer -> modelMapper.map(manufacturer, ManufacturerDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public ManufacturerDto findById(@NonNull Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manufacturer with Id: " + id + " does not exists."));
        return modelMapper.map(manufacturer, ManufacturerDto.class);
    }

    @Override
    public ManufacturerDto findByName(@NonNull String name) {
        Manufacturer manufacturer = manufacturerRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Manufacturer with name: " + name + " does not exists."));
        return modelMapper.map(manufacturer, ManufacturerDto.class);
    }

    @Override
    public ManufacturerDto save(@NonNull ManufacturerDto manufacturerDto) {
        try {
            manufacturerDto.setId(null);

            Set<ProductDto> productsOfManufacturer = manufacturerDto.getProducts();
            manufacturerDto.setProducts(null);

            Manufacturer manufacturer = modelMapper.map(manufacturerDto, Manufacturer.class);
            Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);

            if (productsOfManufacturer != null) {
                Set<Product> savedProducts = new HashSet<>();
                for (ProductDto productDto : productsOfManufacturer) {
                    Product product = modelMapper.map(productDto, Product.class);
                    product.setManufacturer(savedManufacturer);
                    productRepository.save(product);
                    savedProducts.add(product);
                }
                savedManufacturer.setProducts(savedProducts);
            }
            return modelMapper.map(savedManufacturer, ManufacturerDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRecordException("Manufacturer already exists.");
        }
    }

    @Override
    public ManufacturerDto update(@NonNull ManufacturerDto manufacturerDto) {
        if (manufacturerDto.getId() == null) {
            throw new BadRequestException("Id must not be null.");
        }
        Manufacturer manufacturer = modelMapper.map(manufacturerDto, Manufacturer.class);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return modelMapper.map(savedManufacturer, ManufacturerDto.class);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        try {
            manufacturerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RecordNotFoundException("Manufacturer with Id: " + id + " does not exists.");
        }
    }

    @Override
    public void deleteByName(@NonNull String name) {
        try {
            manufacturerRepository.deleteByName(name);
        } catch (Exception e) {
            throw new RecordNotFoundException("Manufacturer with name: " + name + " does not exists.");
        }
    }
}
