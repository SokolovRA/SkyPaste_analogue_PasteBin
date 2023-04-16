package com.example.test_work_4.service;

import com.example.test_work_4.dto.CreatePasteDTO;
import com.example.test_work_4.dto.PasteDTO;
import com.example.test_work_4.dto.PasteUrlDTO;
import com.example.test_work_4.enums.Access;
import com.example.test_work_4.enums.ExpirationTime;
import com.example.test_work_4.exception.PasteNotFoundException;
import com.example.test_work_4.model.Paste;
import com.example.test_work_4.repository.PasteRepository;
import com.example.test_work_4.service.PasteService;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasteServiceTest {
    @Mock
    private PasteRepository pasteRepository;
    @InjectMocks
    private PasteService pasteService;
    private PasteDTO pasteDTO;
    private Paste paste;
    private PasteUrlDTO pasteUrlDTO;
    private CreatePasteDTO createPasteDTO;


    @Test
    public void testCreatePaste_shouldReturnUrlOfCreatedPaste() throws Exception {
        CreatePasteDTO createPasteDTO = new CreatePasteDTO();
        createPasteDTO.setTitle("Test Paste");
        createPasteDTO.setContent("This is a test paste");
        createPasteDTO.setAccess(Access.PUBLIC);
        createPasteDTO.setExpirationTime(ExpirationTime.MONTH_1);

        PasteUrlDTO result = pasteService.createPaste(createPasteDTO);

        assertNotNull(result.getUrl());
    }

    @Test
    public void testGetTenPublicPastes_returnsListOfPublicPastesWithMaximumSizeOfTen() throws Exception {
        List<Paste> pastaList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Paste pasta = new Paste();
            if (i < 5) {
                pasta.setAccess(Access.PUBLIC);
            } else {
                pasta.setAccess(Access.UNLISTED);
            }
            pastaList.add(pasta);
        }
        when(pasteRepository.findAllByStatusPublic())
                .thenReturn(pastaList.subList(0, 5));
        List<PasteDTO> pastaDTOList = pasteService.getTenPublicPastes();
        assertEquals(5, pastaDTOList.size());
    }

    @Test
    public void testFindByUrl() {
        String url = RandomStringUtils.randomAlphabetic(15);
        String title = "Title";
        String content = "Content";
        Instant expiration = Instant.now().plus(Duration.ofMinutes(10));
        Paste paste = new Paste();
        paste.setTitle(title);
        paste.setContent(content);
        paste.setUrl(url);
        paste.setExpiration(expiration);

        PasteDTO expectedPasteDTO = new PasteDTO();
        expectedPasteDTO.setTitle(title);
        expectedPasteDTO.setContent(content);
        expectedPasteDTO.setUrl(url);
        expectedPasteDTO.setExpiration(expiration);
        expectedPasteDTO.setAccess(Access.PUBLIC);

        when(pasteRepository.findPasteByUrl(eq(url)))
                .thenReturn(Optional.of(paste));

        PasteDTO actualPasteDTO = pasteService.searchPastesByUrl(url);
        actualPasteDTO.setAccess(expectedPasteDTO.getAccess());
        assertEquals(expectedPasteDTO, actualPasteDTO);
    }
    @Test
    public void getByTitleOrContent_ReturnsCorrectPasteDTOWithUrl(){
        paste = new Paste();
        paste.setTitle("test title");
        paste.setContent("test content");
        paste.setExpirationTime(ExpirationTime.DAY_1);
        paste.setUrl("test-url");

        pasteDTO = PasteDTO.from(paste);

        createPasteDTO = new CreatePasteDTO();
        createPasteDTO.setTitle("test title");
        createPasteDTO.setContent("test content");
        createPasteDTO.setExpirationTime(ExpirationTime.DAY_1);

        List<Paste> pastes = Collections.singletonList(paste);
        Mockito.when(pasteRepository.findAll(Mockito.any(Specification.class))).thenReturn(pastes);

        List<PasteDTO> result = pasteService.getByTitleOrContent("test title", "test content");

        Mockito.verify(pasteRepository).findAll(Mockito.any(Specification.class));
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(pasteDTO, result.get(0));
        Assertions.assertEquals(paste.getUrl(), result.get(0).getUrl());
    }
}

