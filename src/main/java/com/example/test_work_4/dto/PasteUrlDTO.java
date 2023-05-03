package com.example.test_work_4.dto;

import com.example.test_work_4.model.Paste;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasteUrlDTO {
    private String url;

    public static PasteUrlDTO from (Paste paste) {
        PasteUrlDTO pasteUrlDTO = new PasteUrlDTO();
        pasteUrlDTO.setUrl("http://my-awesome-pastebin.tld/" + paste.getUrl());
        return pasteUrlDTO;
    }
}
