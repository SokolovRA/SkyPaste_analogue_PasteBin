package com.example.test_work_4.controller;

import com.example.test_work_4.DockerConfig;
import com.example.test_work_4.dto.CreatePasteDTO;
import com.example.test_work_4.enums.Access;
import com.example.test_work_4.enums.ExpirationTime;
import com.example.test_work_4.model.Paste;
import com.example.test_work_4.repository.PasteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PasteControllerTest extends DockerConfig {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasteRepository pasteRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Paste paste;

@BeforeEach
void setUp() {
    paste = new Paste();
    paste.setUrl(RandomStringUtils.randomAlphabetic(15));
    paste.setTitle("Title");
    paste.setContent("Body");
    paste.setAccess(Access.PUBLIC);
    paste.setCreated(Instant.now());
    paste.setExpiration(Instant.now().plus(55, ChronoUnit.MINUTES));
    pasteRepository.save(paste);
}

    private JSONObject getJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "имя");
        jsonObject.put("content", "текст");
        jsonObject.put("expirationTime", ExpirationTime.DAY_1.toString());
        jsonObject.put("access", Access.PUBLIC.toString());
        return jsonObject;
    }

    @Test
    void testCreatePaste() throws Exception {
        CreatePasteDTO createPasteDTO = new CreatePasteDTO();
        createPasteDTO.setTitle("Title");
        createPasteDTO.setContent("Body");
        createPasteDTO.setAccess(Access.PUBLIC);
        createPasteDTO.setExpirationTime(ExpirationTime.DAY_1);

        mockMvc.perform(post("/pastes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPasteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").isString())
                .andExpect(jsonPath("$.url").isNotEmpty());
    }

    @Test
    void testGetTenPublicPastes() throws Exception {
        mockMvc.perform(get("/pastes/show-ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetPastesByUrl() throws Exception {
        mockMvc.perform(get("/pastes/" + paste.getUrl()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(paste.getTitle()))
                .andExpect(jsonPath("$.content").value(paste.getContent()));
    }

    @Test
    void testGetByTitleOrContent() throws Exception {
        mockMvc.perform(get("/pastes/search").param("title", "Title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value(paste.getTitle()))
                .andExpect(jsonPath("$[0].content").value(paste.getContent()));
    }
}

