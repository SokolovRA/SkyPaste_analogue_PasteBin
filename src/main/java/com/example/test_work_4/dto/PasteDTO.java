package com.example.test_work_4.dto;

import com.example.test_work_4.enums.Access;
import com.example.test_work_4.model.Paste;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
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
}
