package com.directa24.main.challenge.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DirectorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDirectorsEndpoint() throws Exception {
        mockMvc.perform(get("/movies-api/directors?threshold=4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.directors").isArray())
                .andExpect(jsonPath("$.directors", hasSize(2)))
                .andExpect(jsonPath("$.directors[0]").value("Martin Scorsese"))
                .andExpect(jsonPath("$.directors[1]").value("Woody Allen"));
    }
}
