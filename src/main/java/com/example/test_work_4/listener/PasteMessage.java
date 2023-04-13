package com.example.test_work_4.listener;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class PasteMessage {
    private String pasteId;
    private LocalDateTime deleteAt;
}
