package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ProductTypeDto;
import com.under_waves.dive_shop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/types")
public class ProductTypeController {

    private ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping
    ResponseEntity<Set<ProductTypeDto>> findAll() {
        return ResponseEntity.ok(productTypeService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductTypeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productTypeService.findById(id));
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<ProductTypeDto> findByType(@PathVariable String type) {
        return ResponseEntity.ok(productTypeService.findByType(type));
    }

    @PostMapping
    ResponseEntity<ProductTypeDto> save(@RequestBody @Valid ProductTypeDto productTypeDto) {
        ProductTypeDto savedType = productTypeService.save(productTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedType);
    }

    @PutMapping
    public ResponseEntity<ProductTypeDto> update(@RequestBody @Valid ProductTypeDto productTypeDto) {
        ProductTypeDto updatedType = productTypeService.update(productTypeDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedType);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        productTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{type}")
    public ResponseEntity<HttpStatus> deleteByType(@PathVariable String type) {
        productTypeService.deleteByType(type);
        return ResponseEntity.ok().build();
    }
}
