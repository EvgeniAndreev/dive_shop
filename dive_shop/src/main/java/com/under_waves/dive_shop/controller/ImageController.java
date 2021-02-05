package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ImageDto;
import com.under_waves.dive_shop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public Set<ImageDto> findAll() {
        return imageService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ImageDto findById(@PathVariable Long id) {
        return imageService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ImageDto> save(@RequestBody @Valid ImageDto imageDto) {
        ImageDto savedImage = imageService.save(imageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }

    @PutMapping
    public ResponseEntity<ImageDto> update(@RequestBody @Valid ImageDto imageDto) {
        ImageDto updatedImage = imageService.update(imageDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedImage);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        imageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "delete/{image}")
    public ResponseEntity<HttpStatus> deleteByName(@PathVariable String image) {
        imageService.deleteByName(image);
        return ResponseEntity.ok().build();
    }
}
