package com.example.test_work_4.controller;

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
    public PasteUrlDTO createPaste(@RequestBody PasteDTO pasteDTO,
                                   @RequestParam() Access access,
                                   @RequestParam ExpirationTime expirationTime) {
        return pasteService.createPaste(pasteDTO, access, expirationTime);
    }

    @GetMapping("/show-ten")
    public List <PasteDTO> getTenPublicPastes() {
        return pasteService.getTenPublicPastes();
    }
    @GetMapping("/{url}")
    public PasteDTO getPastesByUrl(@PathVariable String url) {
        return pasteService.searchPastesByUrl(url);
    }
    @GetMapping("/search")
    public ResponseEntity<List<PasteDTO>> getByTitleOrContent(@RequestParam(required = false) String title,
                                                              @RequestParam(required = false) String content){
        return ResponseEntity.ok(pasteService.getByTitleOrContent(title, content));
    }

}
