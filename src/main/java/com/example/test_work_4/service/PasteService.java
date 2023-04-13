package com.example.test_work_4.service;

import com.example.test_work_4.dto.PasteDTO;
import com.example.test_work_4.exception.NotFoundException;
import com.example.test_work_4.listener.MessageSender;
import com.example.test_work_4.listener.PasteMessage;
import com.example.test_work_4.model.Paste;
import com.example.test_work_4.repository.PasteRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasteService {

    private PasteRepository pasteRepository;
    private MessageSender messageSender;

    public PasteService(PasteRepository pasteRepository, MessageSender messageSender) {
        this.pasteRepository = pasteRepository;
        this.messageSender = messageSender;
    }

    public PasteDTO createPaste(PasteDTO pasteDTO) {
        String id = UUID.randomUUID().toString();

        Paste paste = new Paste();
        paste.setId(id);
        paste.setContent(pasteDTO.getContent());
        paste.setCreated_at(LocalDateTime.now());
        paste.setExpiration_time(pasteDTO.getExpiration_time());
        paste.setAccess(pasteDTO.getAccess());
        paste.setUrl("http://my-awesome-pastebin.tld/" + id);
        pasteRepository.save(paste);

        PasteMessage pasteMessage = new PasteMessage();
        pasteMessage.setPasteId(id);
        messageSender.sendMessage(pasteMessage);

        return PasteDTO.from(paste);
    }

    public PasteDTO getPasteById(String id) {
        Optional<Paste> pasteOptional = pasteRepository.findById(id);
        if (!pasteOptional.isPresent()) {
            throw new NotFoundException("Paste not found");
        }
        Paste paste = pasteOptional.get();
        return PasteDTO.from(paste);
    }

    public void deletePasteById(String id) {
        pasteRepository.deleteById(id);
    }

    public List<PasteDTO> getPublicPastes() {
        List<Paste> pastes = pasteRepository.findByAccess("public");
        List<PasteDTO> pasteDTOs = new ArrayList<>();
        for (Paste paste : pastes) {
            pasteDTOs.add(PasteDTO.from(paste));
        }
        return pasteDTOs;
    }
}

