package com.example.test_work_4.dto;

import com.example.test_work_4.enums.Access;
import com.example.test_work_4.model.Paste;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class PasteDTO {
    private String id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime expiration_time;
    private Access access;
    private String url;

    public static PasteDTO from(Paste paste) {
        PasteDTO pasteDTO = new PasteDTO();
        pasteDTO.setId(paste.getId());
        pasteDTO.setContent(paste.getContent());
        pasteDTO.setCreated_at(paste.getCreated_at());
        pasteDTO.setExpiration_time(paste.getExpiration_time());
        pasteDTO.setAccess(paste.getAccess());
        pasteDTO.setUrl(paste.getUrl());
        return pasteDTO;
    }
}
