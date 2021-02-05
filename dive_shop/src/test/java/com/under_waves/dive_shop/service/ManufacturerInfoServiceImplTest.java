package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ManufacturerInfoDto;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.ManufacturerInfo;
import com.under_waves.dive_shop.repository.ManufacturerInfoRepository;
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
class ManufacturerInfoServiceImplTest {

    @Mock
    private ManufacturerInfoRepository manufacturerInfoRepository;
    private ModelMapper modelMapper;

    private ManufacturerInfoServiceImpl manufacturerInfoServiceImpl;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        manufacturerInfoServiceImpl = new ManufacturerInfoServiceImpl(manufacturerInfoRepository, modelMapper);
    }

    @Test
    public void findByIdHappy() {

        Long id = 1L;

        when(manufacturerInfoRepository.findById(eq(id)))
                .thenReturn(Optional.of(ManufacturerInfo.builder().build()));

        ManufacturerInfoDto found = manufacturerInfoServiceImpl.findById(id);

        assertThat(found, is(notNullValue()));
        verify(manufacturerInfoRepository, times(1)).findById(id);
    }

    @Test
    public void findByIdExpectedRecordNotFoundException() {
        Long id = 1L;

        when(manufacturerInfoRepository.findById(eq((Long) id)))
                .thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> manufacturerInfoServiceImpl.findById(id));
    }

    @Test
    public void findByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> manufacturerInfoServiceImpl.findById(null));
    }

    @Test
    void findByPhone() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void saveNPE() {
        assertThrows(NullPointerException.class, () -> manufacturerInfoServiceImpl.save(null));
    }

    @Test
    public void saveVerifyDuplicatedRecordException() {
        ManufacturerInfo info = ManufacturerInfo
                .builder().address("address").email("email").phone("phone").build();
        ManufacturerInfoDto infoDto = ManufacturerInfoDto
                .builder().address("address").email("email").phone("phone").build();

        when(manufacturerInfoRepository.save(eq(info)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> manufacturerInfoServiceImpl.save(infoDto));
    }

    @Test
    public void saveHappy() {
        ManufacturerInfo info = ManufacturerInfo
                .builder().address("address").email("email").phone("phone").build();
        ;
        ManufacturerInfoDto infoDto = ManufacturerInfoDto
                .builder().address("address").email("email").phone("phone").build();

        when(manufacturerInfoRepository.save(eq(info))).thenReturn(info);
        ManufacturerInfoDto actual = manufacturerInfoServiceImpl.save(infoDto);

        assertThat(actual, is(notNullValue()));
        assertEquals("address", actual.getAddress());
    }

    @Test
    void update() {
    }

    @Test
    void deleteByIdHappy() {
        Long id = 1L;

        manufacturerInfoServiceImpl.deleteById(id);

        verify(manufacturerInfoRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> manufacturerInfoServiceImpl.deleteById(null));
    }

    @Test
    void deleteByPhoneHappy() {
        String phone = "somePhone";

        manufacturerInfoServiceImpl.deleteByPhone(phone);

        verify(manufacturerInfoRepository, times(1)).deleteByPhone(phone);
    }

    @Test
    public void deleteByPhoneWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> manufacturerInfoServiceImpl.deleteByPhone(null));
    }

    @Test
    void deleteByEmailHappy() {
        String email = "somePhone";

        manufacturerInfoServiceImpl.deleteByPhone(email);

        verify(manufacturerInfoRepository, times(1)).deleteByPhone(email);
    }

    @Test
    public void deleteByEmailWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> manufacturerInfoServiceImpl.deleteByPhone(null));
    }
}