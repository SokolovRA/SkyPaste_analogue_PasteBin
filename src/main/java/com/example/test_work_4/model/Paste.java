package com.example.test_work_4.model;

import com.example.test_work_4.enums.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
public class Paste {
    @Id
    private String id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime expiration_time;
    @Enumerated(EnumType.STRING)
    private Access access;
    private String url;

}
