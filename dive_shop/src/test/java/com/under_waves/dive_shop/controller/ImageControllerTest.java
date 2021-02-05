package com.under_waves.dive_shop.controller;

import com.under_waves.dive_shop.dto.ImageDto;
import com.under_waves.dive_shop.model.Image;
import com.under_waves.dive_shop.service.ImageServiceImpl;
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

@WebMvcTest(ImageController.class)
public class ImageControllerTest extends BaseControllerTest {

    @MockBean
    private ImageServiceImpl imageServiceImpl;

    @Test
    public void findAll() throws Exception {
        ImageDto image = ImageDto.builder().id(1L).name("image").build();

        when(imageServiceImpl.findAll()).thenReturn(new HashSet<ImageDto>(Collections.singletonList(image)));

        mvc.perform(get("/images"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void findAllWhenEmpty() throws Exception {
        when(imageServiceImpl.findAll()).thenReturn(Collections.emptySet());

        mvc.perform(get("/images"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(is(empty()))));
    }

    @Test
    public void findById() throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1L);
        imageDto.setName("pic");

        when(imageServiceImpl.findById(1L)).thenReturn(imageDto);

        mvc.perform(get("/images/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("pic")));
    }

    @Test
    public void saveHappy() throws Exception {
        ImageDto request = ImageDto.builder().name("pic").build();
        String requestJson = objectMapper.writeValueAsString(request);
        ImageDto response = ImageDto.builder()
                .id(1L)
                .name(request.getName())
                .build();

        when(imageServiceImpl.save(request)).thenReturn(response);
        String responseJson = objectMapper.writeValueAsString(response);
        mvc.perform(post("/images")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("pic")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(content().json(responseJson, true));
    }

    @Test
    public void saveWhenHaveNullPropertyInObject() throws Exception {
        ImageDto request = ImageDto.builder().name(null).build();
        String requestJson = objectMapper.writeValueAsString(request);

        mvc.perform(post("/images")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveWhenNull() throws Exception {
        mvc.perform(post("/images")
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