package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ManufacturerDto;
import com.under_waves.dive_shop.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    ResponseEntity<Set<ManufacturerDto>> findAll() {
        return ResponseEntity.ok(manufacturerService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ManufacturerDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(manufacturerService.findById(id));
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<ManufacturerDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(manufacturerService.findByName(name));
    }

    @PostMapping
    ResponseEntity<ManufacturerDto> save(@RequestBody @Valid ManufacturerDto manufacturerDto) {
        ManufacturerDto savedManufacturer = manufacturerService.save(manufacturerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManufacturer);
    }

    @PutMapping
    public ResponseEntity<ManufacturerDto> update(@RequestBody @Valid ManufacturerDto manufacturerDto) {
        ManufacturerDto updatedManufacturer = manufacturerService.update(manufacturerDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedManufacturer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        manufacturerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{name}")
    public ResponseEntity<HttpStatus> deleteByName(@PathVariable String name) {
        manufacturerService.deleteByName(name);
        return ResponseEntity.ok().build();
    }
}
