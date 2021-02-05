package com.under_waves.dive_shop.service;

import com.under_waves.dive_shop.dto.ImageDto;
import com.under_waves.dive_shop.exception.BadRequestException;
import com.under_waves.dive_shop.exception.DuplicateRecordException;
import com.under_waves.dive_shop.exception.RecordNotFoundException;
import com.under_waves.dive_shop.model.Image;
import com.under_waves.dive_shop.repository.ImageRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ImageDto> findAll() {
        return imageRepository.findAll()
                .stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toSet());

    }

    @Override
    public ImageDto findById(@NonNull Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Image with ID: " + id + "does not exists."));
        return modelMapper.map(image, ImageDto.class);
    }

    @Override
    public ImageDto findByName(@NonNull String name) {
        Image image = imageRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Image with Name: " + name + "does not exists."));
        return modelMapper.map(image, ImageDto.class);
    }

    @Override
    public ImageDto save(@NonNull ImageDto imageDto) {
        try {
            imageDto.setId(null);
            Image image = modelMapper.map(imageDto, Image.class);
            imageRepository.save(image);
            return modelMapper.map(image, ImageDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRecordException("Image already exists.");
        }
    }

    @Override
    public ImageDto update(@NonNull ImageDto imageDto) {
        if (imageDto.getId() == null) {
            throw new BadRequestException("Id must not be null.");
        }
        Image image = modelMapper.map(imageDto, Image.class);
        Image savedImage = imageRepository.save(image);
        return modelMapper.map(savedImage, ImageDto.class);
    }

    @Override
    public void deleteById(@NonNull @Valid Long id) {
        try {
            imageRepository.deleteById(id);
        } catch (Exception e) {
            throw new RecordNotFoundException("Image with Id: " + id + " does not exists.");
        }
    }

    @Override
    public void deleteByName(@NonNull String name) {
        try {
            imageRepository.deleteByName(name);
        } catch (Exception e) {
            throw new RecordNotFoundException("Image with name: " + name + " does not exists.");
        }
    }
}
