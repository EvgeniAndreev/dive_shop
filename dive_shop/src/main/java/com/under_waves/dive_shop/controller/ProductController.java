package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ProductDto;
import com.under_waves.dive_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    ResponseEntity<Set<ProductDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping(value = "/barcode/{barCode}")
    public ResponseEntity<ProductDto> findByBarCode(@PathVariable String barCode) {
        return ResponseEntity.ok(productService.findByBarCode(barCode));
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<ProductDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @PostMapping
    ResponseEntity<ProductDto> save(@RequestBody @Valid ProductDto productDto) {
        ProductDto savedProduct = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody @Valid ProductDto productDto) {
        ProductDto updatedProduct = productService.update(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{barcode}")
    public ResponseEntity<HttpStatus> deleteByBarcode(@PathVariable String barcode) {
        productService.deleteByBarcode(barcode);
        return ResponseEntity.ok().build();
    }
}
