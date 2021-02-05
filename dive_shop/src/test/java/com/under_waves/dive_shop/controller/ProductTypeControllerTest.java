package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ProductTypeDto;
import com.under_waves.dive_shop.service.ProductTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductTypeController.class)
public class ProductTypeControllerTest extends BaseControllerTest {

    @MockBean
    private ProductTypeServiceImpl productTypeServiceImpl;

    @Test
    public void findAll() throws Exception {
        ProductTypeDto productTypeDto = ProductTypeDto.builder().id(1L).type("productType").build();

        when(productTypeServiceImpl.findAll()).thenReturn(new HashSet<ProductTypeDto>(Collections.singletonList(productTypeDto)));

        mvc.perform(get("/types"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findAllWhenEmpty() throws Exception {
        when(productTypeServiceImpl.findAll()).thenReturn(Collections.emptySet());

        mvc.perform(get("/types"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(is(empty()))));
    }

    @Test
    public void findById() throws Exception {
        ProductTypeDto productTypeDto = new ProductTypeDto();
        productTypeDto.setId(1L);
        productTypeDto.setType("type");

        when(productTypeServiceImpl.findById(1L)).thenReturn(productTypeDto);

        mvc.perform(get("/types/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", is("type")));
    }

    @Test
    void findByType() {
    }

    @Test
    public void saveHappy() throws Exception {
        ProductTypeDto request = ProductTypeDto.builder().type("type").build();
        String requestJson = objectMapper.writeValueAsString(request);
        ProductTypeDto response = ProductTypeDto.builder()
                .id(1L)
                .type(request.getType())
                .build();

        when(productTypeServiceImpl.save(request)).thenReturn(response);
        String responseJson = objectMapper.writeValueAsString(response);
        mvc.perform(post("/types")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", is("type")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(content().json(responseJson, true));
    }

    @Test
    public void saveWhenHaveNullPropertyInObject() throws Exception {
        ProductTypeDto request = ProductTypeDto.builder().type(null).build();

        String requestJson = objectMapper.writeValueAsString(request);

        mvc.perform(post("/types")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveWhenNull() throws Exception {
        mvc.perform(post("/types")
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
    void deleteByType() {
    }
}