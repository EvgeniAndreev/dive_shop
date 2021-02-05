package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ImageDto;
import com.under_waves.dive_shop.dto.ProductDto;
import com.under_waves.dive_shop.exception.BadRequestException;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Image;
import com.under_waves.dive_shop.model.Product;
import com.under_waves.dive_shop.repository.ImageRepository;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public ProductDto findById(@NonNull Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product with Id: " + id + " does not exists."));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto findByBarCode(@NonNull String barCode) {
        Product product = productRepository.findByBarCode(barCode)
                .orElseThrow(() -> new RecordNotFoundException("Product with Barcode: " + barCode + " does not exists."));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto findByName(@NonNull String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Product with Name: " + name + " does not exists."));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto save(@NonNull ProductDto productDto) {
        try {
            productDto.setId(null);
            Set<ImageDto> imagesForProduct = productDto.getImages();
            productDto.setImages(null);
            Product product = modelMapper.map(productDto, Product.class);
            Product savedProduct = productRepository.save(product);
            if (imagesForProduct != null) {
                Set<Image> savedImages = new HashSet<>();
                for (ImageDto imageDto : imagesForProduct) {
                    Image image = modelMapper.map(imageDto, Image.class);
                    image.setProduct(savedProduct);
                    imageRepository.save(image);
                    savedImages.add(imageRepository.save(image));
                }
                savedProduct.setImages(savedImages);
            }
            return modelMapper.map(savedProduct, ProductDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRecordException("Product already exists.");
        }
    }

    @Override
    public ProductDto update(@NonNull ProductDto productDto) {
        if (productDto.getId() == null) {
            throw new BadRequestException("Id must not be null.");
        }
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RecordNotFoundException("A product with ID: " + id + " already exists.");
        }
    }

    @Override
    public void deleteByBarcode(@NonNull String barCode) {
        try {
            productRepository.deleteByBarCode(barCode);
        } catch (Exception e) {
            throw new RecordNotFoundException("A product with Barcode: " + barCode + " already exists.");
        }
    }
}
