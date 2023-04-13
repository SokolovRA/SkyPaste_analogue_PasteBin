package com.example.test_work_4.controller;

import com.example.test_work_4.dto.PasteDTO;
import com.example.test_work_4.service.PasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pastes")
public class PasteController {
    private PasteService pasteService;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    public ResponseEntity<PasteDTO> createPaste(@RequestBody PasteDTO pasteDTO) {
        PasteDTO createdPaste = pasteService.createPaste(pasteDTO);
        return new ResponseEntity<>(createdPaste, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasteDTO> getPasteById(@PathVariable String id) {
        PasteDTO paste = pasteService.getPasteById(id);
        return new ResponseEntity<>(paste, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePasteById(@PathVariable String id) {
        pasteService.deletePasteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


