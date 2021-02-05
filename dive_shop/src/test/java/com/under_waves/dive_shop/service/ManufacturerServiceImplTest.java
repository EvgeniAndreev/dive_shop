package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ManufacturerDto;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Manufacturer;
import com.under_waves.dive_shop.repository.ManufacturerRepository;
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
class ManufacturerServiceImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    private ManufacturerServiceImpl manufacturerServiceImpl;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        manufacturerServiceImpl = new ManufacturerServiceImpl(manufacturerRepository, productRepository, modelMapper);
    }

    @Test
    void findAll() {
    }

    @Test
    public void findByIdHappy() {

        Long id = 1L;

        when(manufacturerRepository.findById(eq(id)))
                .thenReturn(Optional.of(Manufacturer.builder().build()));

        ManufacturerDto found = manufacturerServiceImpl.findById(id);

        assertThat(found, is(notNullValue()));
        verify(manufacturerRepository, times(1)).findById(id);
    }

    @Test
    public void findByIdExpectedRecordNotFoundException() {
        Long id = 1L;

        when(manufacturerRepository.findById(eq((Long) id)))
                .thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> manufacturerServiceImpl.findById(id));
    }

    @Test
    public void finByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> manufacturerServiceImpl.findById(null));
    }

    @Test
    void findByName() {
    }

    @Test
    void saveNPE() {
        assertThrows(NullPointerException.class, () -> manufacturerServiceImpl.save(null));
    }

    @Test
    public void verifyDuplicatedRecordException() {

        Manufacturer info = Manufacturer
                .builder().name("name").build();
        ManufacturerDto infoDto = ManufacturerDto
                .builder().name("name").build();

        when(manufacturerRepository.save(eq(info)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> manufacturerServiceImpl.save(infoDto));
    }

    @Test
    public void saveHappy() {
        Manufacturer manufacturer = Manufacturer
                .builder().name("name").build();
        ManufacturerDto manufacturerDto = ManufacturerDto
                .builder().name("name").build();

        when(manufacturerRepository.save(eq(manufacturer))).thenReturn(manufacturer);
        ManufacturerDto actual = manufacturerServiceImpl.save(manufacturerDto);

        assertThat(actual, is(notNullValue()));
        assertEquals("name", actual.getName());
    }

    @Test
    void update() {
    }

    @Test
    void deleteByIdHappy() {
        Long id = 1L;

        manufacturerServiceImpl.deleteById(id);

        verify(manufacturerRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> manufacturerServiceImpl.deleteById(null));
    }

    @Test
    void deleteByNameHappy() {
        String phone = "somePhone";

        manufacturerServiceImpl.deleteByName(phone);

        verify(manufacturerRepository, times(1)).deleteByName(phone);
    }

    @Test
    public void deleteByPhoneWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> manufacturerServiceImpl.deleteByName(null));
    }
}
