package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ProductTypeDto;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.ProductType;
import com.under_waves.dive_shop.repository.ProductTypeRepository;
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
class ProductTypeServiceImplTest {

    @Mock
    private ProductTypeRepository productTypeRepository;
    private ModelMapper modelMapper;

    private ProductTypeServiceImpl productTypeServiceImpl;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        productTypeServiceImpl = new ProductTypeServiceImpl(productTypeRepository, modelMapper);
    }

    @Test
    void findAll() {
    }

    @Test
    public void findByIdHappy() {

        Long id = 1L;

        when(productTypeRepository.findById(eq(id)))
                .thenReturn(Optional.of(ProductType.builder().build()));

        ProductTypeDto found = productTypeServiceImpl.findById(id);

        assertThat(found, is(notNullValue()));
        verify(productTypeRepository, times(1)).findById(id);
    }

    @Test
    public void findByIdExpectedRecordNotFoundException() {
        Long id = 1L;

        when(productTypeRepository.findById(eq((Long) id)))
                .thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> productTypeServiceImpl.findById(id));
    }

    @Test
    public void findByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> productTypeServiceImpl.findById(null));
    }

    @Test
    void findByType() {
    }

    @Test
    void saveNPE() {
        assertThrows(NullPointerException.class, () -> productTypeServiceImpl.save(null));
    }

    @Test
    public void verifyDuplicatedRecordException() {

        ProductType productType = ProductType
                .builder().type("type").build();
        ProductTypeDto productTypeDto = ProductTypeDto
                .builder().type("type").build();

        when(productTypeRepository.save(eq(productType)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> productTypeServiceImpl.save(productTypeDto));
    }

    @Test
    public void saveHappy() {
        ProductType productType = ProductType
                .builder().type("type").build();
        ProductTypeDto productTypeDto = ProductTypeDto
                .builder().type("type").build();

        when(productTypeRepository.save(eq(productType))).thenReturn(productType);
        ProductTypeDto actual = productTypeServiceImpl.save(productTypeDto);

        assertThat(actual, is(notNullValue()));
        assertEquals("type", actual.getType());
    }

    @Test
    void update() {
    }

    @Test
    void deleteByIdHappy() {
        Long id = 1L;

        productTypeServiceImpl.deleteById(id);

        verify(productTypeRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> productTypeServiceImpl.deleteById(null));
    }

    @Test
    void deleteByTypeHappy() {
        String phone = "somePhone";

        productTypeServiceImpl.deleteByType(phone);

        verify(productTypeRepository, times(1)).deleteByType(phone);
    }
}