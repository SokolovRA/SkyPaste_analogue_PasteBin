package com.example.test_work_4.controller;

import com.example.test_work_4.dto.CreatePasteDTO;
import com.example.test_work_4.dto.PasteDTO;
import com.example.test_work_4.dto.PasteUrlDTO;
import com.example.test_work_4.enums.Access;
import com.example.test_work_4.enums.ExpirationTime;
import com.example.test_work_4.service.PasteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pastes")
public class PasteController {
    private PasteService pasteService;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    public PasteUrlDTO createPaste(@RequestBody CreatePasteDTO createPasteDTO) {
        return pasteService.createPaste(createPasteDTO);
    }
    @GetMapping("/show-ten")
    public List <PasteDTO> getTenPublicPastes() {
        return pasteService.getTenPublicPastes();
    }
    @GetMapping("/{url}")
    public ResponseEntity<PasteDTO> getPastesByUrl(@PathVariable String url) {
        try {
            PasteDTO paste = pasteService.searchPastesByUrl(url);
            return ResponseEntity.ok(paste);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<PasteDTO>> getByTitleOrContent(@RequestParam(required = false) String title,
                                                              @RequestParam(required = false) String content){
        try {
            List<PasteDTO> pastes = pasteService.getByTitleOrContent(title, content);
            return ResponseEntity.ok(pastes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}