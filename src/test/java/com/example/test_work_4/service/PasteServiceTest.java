package com.example.test_work_4.service;

import com.example.test_work_4.config.DockerConfig;
import com.example.test_work_4.dto.CreatePasteDTO;
import com.example.test_work_4.dto.PasteDTO;
import com.example.test_work_4.dto.PasteUrlDTO;
import com.example.test_work_4.enums.Access;
import com.example.test_work_4.enums.ExpirationTime;
import com.example.test_work_4.model.Paste;
import com.example.test_work_4.repository.PasteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Testcontainers
public class PasteServiceTest extends DockerConfig {
    @Autowired
    private PasteService pasteService;
    @Autowired
    private PasteRepository pasteRepository;

    @Test
    public void testCreatePaste_shouldReturnUrlOfCreatedPaste() {
        CreatePasteDTO createPasteDTO = new CreatePasteDTO();
        createPasteDTO.setTitle("Test Paste");
        createPasteDTO.setContent("This is a test paste");
        createPasteDTO.setAccess(Access.PUBLIC);
        createPasteDTO.setExpirationTime(ExpirationTime.MONTH_1);

        PasteUrlDTO result = pasteService.createPaste(createPasteDTO);

        assertNotNull(result.getUrl());
    }
    @Test
    public void testSearchPastesByUrl_ReturnsCorrectPasteDTOWithAccess() {
        String url = RandomStringUtils.randomAlphabetic(15);
        String title = "Title";
        String content = "Content";
        Instant expiration = Instant.now().plus(Duration.ofMinutes(10));
        Paste paste = new Paste();
        paste.setTitle(title);
        paste.setContent(content);
        paste.setUrl(url);
        paste.setExpiration(expiration);
        paste.setAccess(Access.PUBLIC);
        pasteRepository.save(paste);

        PasteDTO expectedPasteDTO = new PasteDTO();
        expectedPasteDTO.setTitle(title);
        expectedPasteDTO.setContent(content);
        expectedPasteDTO.setUrl(url);
        expectedPasteDTO.setExpiration(expiration);
        expectedPasteDTO.setAccess(Access.PUBLIC);

        PasteDTO actualPasteDTO = pasteService.searchPastesByUrl(url);

        assertEquals(expectedPasteDTO, actualPasteDTO);
    }
}


