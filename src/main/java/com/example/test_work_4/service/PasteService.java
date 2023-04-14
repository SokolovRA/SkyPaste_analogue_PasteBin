package com.example.test_work_4.service;

import com.example.test_work_4.dto.PasteDTO;
import com.example.test_work_4.dto.PasteUrlDTO;
import com.example.test_work_4.enums.Access;
import com.example.test_work_4.enums.ExpirationTime;
import com.example.test_work_4.model.Paste;
import com.example.test_work_4.repository.PasteRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.test_work_4.repository.specification.SpecificationPaste.byBody;
import static com.example.test_work_4.repository.specification.SpecificationPaste.byTitle;

@Service
public class PasteService {

    private PasteRepository pasteRepository;


    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    public PasteUrlDTO createPaste(PasteDTO pasteDTO, Access access, ExpirationTime expirationTime) {
        Paste paste = pasteDTO.to();
        paste.setUrl(UUID.randomUUID().toString());
        paste.setExpiration(Instant.now().plus(expirationTime.getDuration()));
        paste.setAccess(access);
        pasteRepository.save(paste);
        return PasteUrlDTO.from(paste);
    }

    public List<PasteDTO> getTenPublicPastes() {
        return pasteRepository.findAllByStatusPublic().stream().map(PasteDTO::from).collect(Collectors.toList());
    }
    public PasteDTO searchPastesByUrl(String url) {
        Paste paste = pasteRepository.findPasteByUrl(url).orElseThrow(RuntimeException::new);
        return PasteDTO.from(paste);
    }
    public List<PasteDTO> getByTitleOrContent(String title, String content) {
        return pasteRepository.findAll(Specification.where(
                                byTitle(title))
                        .and(byBody(content)))
                .stream()
                .map(PasteDTO::from)
                .collect(Collectors.toList());
    }

}

