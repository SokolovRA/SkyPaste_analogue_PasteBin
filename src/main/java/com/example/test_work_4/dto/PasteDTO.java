package com.example.test_work_4.dto;

import com.example.test_work_4.enums.Access;
import com.example.test_work_4.model.Paste;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
@Getter
@Setter
public class PasteDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String url;
    private String title;
    private String content;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant expiration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private Access access;
    public static PasteDTO from(Paste paste) {
        PasteDTO dto = new PasteDTO();
        dto.setUrl(paste.getUrl());
        dto.setContent(paste.getContent());
        dto.setTitle(paste.getTitle());
        dto.setExpiration(paste.getExpiration());
        dto.setAccess(paste.getAccess());
        return dto; }
    public Paste to() {
        Paste paste = new Paste();
        paste.setUrl(this.getUrl());
        paste.setContent(this.getContent());
        paste.setTitle(this.getTitle());
        paste.setExpiration(this.getExpiration());
        paste.setAccess(this.getAccess());
        return paste;
    }
}
