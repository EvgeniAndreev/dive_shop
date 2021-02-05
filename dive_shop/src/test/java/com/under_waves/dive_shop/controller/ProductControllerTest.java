package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ImageDto;
import com.under_waves.dive_shop.dto.ManufacturerDto;
import com.under_waves.dive_shop.dto.ProductDto;
import com.under_waves.dive_shop.service.ManufacturerServiceImpl;
import com.under_waves.dive_shop.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerTest extends BaseControllerTest {

    @MockBean
    private ProductServiceImpl productServiceImpl;

    @Test
    public void findAll() throws Exception {
        ProductDto productDto = ProductDto.builder().id(1L).name("manufacturer").build();

        when(productServiceImpl.findAll()).thenReturn(new HashSet<ProductDto>(Collections.singletonList(productDto)));

        mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findAllWhenEmpty() throws Exception {
        when(productServiceImpl.findAll()).thenReturn(Collections.emptySet());

        mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(is(empty()))));
    }

    @Test
    public void findById() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("product");

        when(productServiceImpl.findById(1L)).thenReturn(productDto);

        mvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("product")));
    }

    @Test
    void findByBarCode() {
    }

    @Test
    void findByName() {
    }

    @Test
    public void saveHappy() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("product").size("XL").barCode("0123456789123").quantity(2).build();
        String requestJson = objectMapper.writeValueAsString(request);
        ProductDto response = ProductDto.builder()
                .id(1L)
                .name(request.getName())
                .build();

        when(productServiceImpl.save(request)).thenReturn(response);
        String responseJson = objectMapper.writeValueAsString(response);
        mvc.perform(post("/products")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("product")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(content().json(responseJson, true));
    }

    @Test
    public void saveWhenHaveNullPropertyInObject() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("product").size("XL").barCode(null).quantity(2).build();

        String requestJson = objectMapper.writeValueAsString(request);

        mvc.perform(post("/products")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveWhenNull() throws Exception {
        mvc.perform(post("/products")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteByBarcode() {
    }
}