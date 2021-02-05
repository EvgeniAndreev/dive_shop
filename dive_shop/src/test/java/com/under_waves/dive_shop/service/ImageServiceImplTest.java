package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ImageDto;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Image;
import com.under_waves.dive_shop.repository.ImageRepository;
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
public class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;
    private ModelMapper modelMapper;

    private ImageServiceImpl imageServiceImpl;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        imageServiceImpl = new ImageServiceImpl(imageRepository, modelMapper);
    }

    @Test
    public void findByIdHappy() {

        Long id = 1L;

        when(imageRepository.findById(eq(id)))
                .thenReturn(Optional.of(Image.builder().build()));

        ImageDto found = imageServiceImpl.findById(id);

        assertThat(found, is(notNullValue()));
        verify(imageRepository, times(1)).findById(id);
    }

    @Test
    public void findByIdExpectedRecordNotFoundException() {
        Long id = 1L;

        when(imageRepository.findById(eq((Long) id)))
                .thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> imageServiceImpl.findById(id));
    }

    @Test
    public void findByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> imageServiceImpl.findById(null));
    }

    @Test
    public void saveExpectNPE() {
        assertThrows(NullPointerException.class, () -> imageServiceImpl.save(null));
    }

    @Test
    public void saveVerifyDuplicatedRecordException() {
        ImageDto imageDto = ImageDto.builder().name("someName").build();
        Image image = Image.builder().name("someName").build();

        when(imageRepository.save(eq(image)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateRecordException.class, () -> imageServiceImpl.save(imageDto));
    }

    @Test
    public void saveHappy() {
        Image image = Image.builder().name("someImage").build();
        ImageDto imageDto = ImageDto.builder().name("someImage").build();

        when(imageRepository.save(eq(image))).thenReturn(image);

        ImageDto actual = imageServiceImpl.save(imageDto);

        assertThat(actual, is(notNullValue()));
        assertEquals("someImage", actual.getName());
    }

    @Test
    public void updateHappy() {
        Image image = Image.builder().name("someImage").build();
        ImageDto imageDto = ImageDto.builder().name("someImage").build();

        when(imageRepository.save(eq(image))).thenReturn(image);

        ImageDto actual = imageServiceImpl.save(imageDto);

        assertThat(actual, is(notNullValue()));
        assertEquals("someImage", actual.getName());
    }

    @Test
    public void deleteByIdHappy() {
        Long id = 1L;

        imageServiceImpl.deleteById(id);

        verify(imageRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteByIdWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> imageServiceImpl.deleteById(null));
    }

    @Test
    public void deleteByNameHappy() {
        String name = "Test";

        imageServiceImpl.deleteByName(name);

        verify(imageRepository, times(1)).deleteByName(name);
    }

    @Test
    public void deleteByNameWhenNullIsPassed() {
        assertThrows(NullPointerException.class, () -> imageServiceImpl.deleteByName(null));
    }
}