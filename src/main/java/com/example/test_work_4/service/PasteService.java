package com.example.test_work_4.service;

import com.example.test_work_4.dto.CreatePasteDTO;
import com.example.test_work_4.dto.PasteDTO;
import com.example.test_work_4.dto.PasteUrlDTO;
import com.example.test_work_4.exception.PasteBadRequestException;
import com.example.test_work_4.exception.PasteNotFoundException;
import com.example.test_work_4.model.Paste;
import com.example.test_work_4.repository.PasteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.test_work_4.repository.specification.SpecificationPaste.*;


@Service
public class PasteService {
    private final PasteRepository pasteRepository;
    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    public PasteUrlDTO createPaste(CreatePasteDTO createPasteDTO) {
        Paste paste = createPasteDTO.to();
        paste.setUrl(RandomStringUtils.randomAlphabetic(15));
        paste.setExpiration(Instant.now().plus(createPasteDTO.getExpirationTime().getDuration()));
        paste.setAccess(createPasteDTO.getAccess());
        pasteRepository.save(paste);
        return PasteUrlDTO.from(paste);
    }
    public List<PasteDTO> getTenPublicPastes() {
        return pasteRepository.findAllByStatusPublic().stream().map(PasteDTO::from).collect(Collectors.toList());
    }
    public PasteDTO searchPastesByUrl(String url) {
        Paste paste = pasteRepository.findPasteByUrl(url)
                .filter(p -> p.getExpiration().isAfter(Instant.now()))
                .orElseThrow(PasteNotFoundException::new);
        return PasteDTO.from(paste);
    }
    public List<PasteDTO> getByTitleOrContent(String title, String content) {
        List<Paste> pastes = pasteRepository.findAll(Specification.where(
                        byTitle(title))
                .and(byBody(content))
                .and(byNotExpired()));
        if (pastes.isEmpty()) {
            throw new PasteBadRequestException();
        }
        return pastes.stream()
                .map(PasteDTO::from)
                .collect(Collectors.toList());
    }
}


