package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ManufacturerDto;
import com.under_waves.dive_shop.service.ManufacturerServiceImpl;
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

@WebMvcTest(ManufacturerController.class)
public class ManufacturerControllerTest extends BaseControllerTest {

    @MockBean
    private ManufacturerServiceImpl manufacturerServiceImpl;

    @Test
    public void findAll() throws Exception {
        ManufacturerDto manufacturer = ManufacturerDto.builder().id(1L).name("manufacturer").build();

        when(manufacturerServiceImpl.findAll()).thenReturn(new HashSet<ManufacturerDto>(Collections.singletonList(manufacturer)));

        mvc.perform(get("/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findAllWhenEmpty() throws Exception {
        when(manufacturerServiceImpl.findAll()).thenReturn(Collections.emptySet());

        mvc.perform(get("/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(is(empty()))));
    }

    @Test
    public void findById() throws Exception {
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(1L);
        manufacturerDto.setName("manufacturer");

        when(manufacturerServiceImpl.findById(1L)).thenReturn(manufacturerDto);

        mvc.perform(get("/manufacturers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("manufacturer")));
    }

    @Test
    void findByName() {
    }

    @Test
    public void saveHappy() throws Exception {
        ManufacturerDto request = ManufacturerDto.builder().name("manufacturer").build();
        String requestJson = objectMapper.writeValueAsString(request);
        ManufacturerDto response = ManufacturerDto.builder()
                .id(1L)
                .name(request.getName())
                .build();

        when(manufacturerServiceImpl.save(request)).thenReturn(response);
        String responseJson = objectMapper.writeValueAsString(response);
        mvc.perform(post("/manufacturers")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("manufacturer")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(content().json(responseJson, true));
    }

    @Test
    public void saveWhenHaveNullPropertyInObject() throws Exception {
        ManufacturerDto request = ManufacturerDto.builder().name(null).build();
        String requestJson = objectMapper.writeValueAsString(request);

        mvc.perform(post("/manufacturers")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveWhenNull() throws Exception {
        mvc.perform(post("/manufacturers")
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
    void deleteByName() {
    }
}