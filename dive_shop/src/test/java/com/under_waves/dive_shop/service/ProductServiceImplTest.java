package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ProductDto;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Product;
import com.under_waves.dive_shop.repository.ImageRepository;
import com.under_waves.dive_shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    private ImageRepository imageRepository;
    private ModelMapper modelMapper;

    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        productServiceImpl = new ProductServiceImpl(productRepository, imageRepository, modelMapper);
    }

    @Test
    void findAll() {
    }

    @Test
    public void findByIdHappy() {

        Long id = 1L;

        when(productRepository.findById(eq(id)))
                .thenReturn(Optional.of(Product.builder().build()));

        ProductDto found = productServiceImpl.findById(id);

        assertThat(found, is(notNullValue()));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void findByIdExpectedRecordNotFoundException() {
        Long id = 1L;

        when(productRepository.findById(eq((Long) id)))
                .thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> productServiceImpl.findById(id));
    }

    @Test
    public void findByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> productServiceImpl.findById(null));
    }

    @Test
    void findByBarCode() {
    }

    @Test
    void findByName() {
    }

    @Test
    void saveNPE() {
        assertThrows(NullPointerException.class, () -> productServiceImpl.save(null));
    }

    @Test
    public void verifyDuplicatedRecordException() {

        Product product = Product
                .builder().name("name").build();
        ProductDto productDto = ProductDto
                .builder().name("name").build();

        when(productRepository.save(eq(product)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> productServiceImpl.save(productDto));
    }

    @Test
    public void saveHappy() {
        Product product = Product
                .builder().name("name").build();
        ProductDto productDto = ProductDto
                .builder().name("name").build();

        when(productRepository.save(eq(product))).thenReturn(product);
        ProductDto actual = productServiceImpl.save(productDto);

        assertThat(actual, is(notNullValue()));
        assertEquals("name", actual.getName());
    }

    @Test
    void update() {
    }

    @Test
    void deleteByIdHappy() {
        Long id = 1L;

        productServiceImpl.deleteById(id);

        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> productServiceImpl.deleteById(null));
    }

    @Test
    void deleteByBarCodeHappy() {
        String phone = "somePhone";

        productServiceImpl.deleteByBarcode(phone);

        verify(productRepository, times(1)).deleteByBarCode(phone);
    }

    @Test
    public void deleteByBarCodeWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> productServiceImpl.deleteByBarcode(null));
    }
}