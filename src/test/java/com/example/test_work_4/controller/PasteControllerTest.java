package com.example.test_work_4.controller;

import com.example.test_work_4.dto.CreatePasteDTO;
import com.example.test_work_4.enums.Access;
import com.example.test_work_4.enums.ExpirationTime;
import com.example.test_work_4.model.Paste;
import com.example.test_work_4.repository.PasteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PasteControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasteRepository pasteRepository;
    @Autowired
    ObjectMapper objectMapper;
    Paste paste;
    Access access = Access.PUBLIC;
    ExpirationTime expirationTime = ExpirationTime.MINUTES_10;
    @BeforeEach
    void setUp() {
        paste = new Paste();
        paste.setUrl(RandomStringUtils.randomAlphabetic(15));
        paste.setTitle("Title");
        paste.setContent("Body");
        paste.setAccess(access);
        paste.setCreated(Instant.now());
        paste.setExpiration(Instant.now().plus(expirationTime.getDuration()));
        paste.setExpirationTime(expirationTime);
        pasteRepository.save(paste);

    }
    @AfterEach
    void tearDown() {
        pasteRepository.deleteAll();
    }
    @Test
    void testCreatePaste() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/pastes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreatePasteDTO.from(paste)))
                        .param("expirationTime", expirationTime.toString())
                        .param("access", access.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").isString());
    }
    @Test
    void testGetTenPublicPastes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pastes/show-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetPastesByUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pastes/" + paste.getUrl()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(paste.getUrl()))
                .andExpect(jsonPath("$.title").value(paste.getTitle()))
                .andExpect(jsonPath("$.content").value(paste.getContent()))
                .andExpect(jsonPath("$.access").value(paste.getAccess().toString()))
                .andExpect(jsonPath("$.expiration").isString());
    }
    @Test
    void testGetByTitleOrContent() throws Exception {
        String title = "Title";
        String content = "Body";
        mockMvc.perform(MockMvcRequestBuilders.get("/pastes/search")
                        .param("title", title)
                        .param("content", content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value(title))
                .andExpect(jsonPath("$.[0].content").value(content));
    }
}