package com.under_waves.dive_shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BaseControllerTest {

    private static final String EMPTY_COLLECTION = "[]";
    private static final String EMPTY_JSON = "";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

}
