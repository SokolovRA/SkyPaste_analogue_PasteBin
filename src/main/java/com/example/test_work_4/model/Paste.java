package com.example.test_work_4.model;

import com.example.test_work_4.enums.Access;
import com.example.test_work_4.enums.ExpirationTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Paste {
    @Id
    private String url;
    private String title;
    private String content;
    private Instant created = Instant.now();
    private Instant expiration;
    @Enumerated(EnumType.STRING)
    private Access access;
    @Enumerated(EnumType.STRING)
    private ExpirationTime expirationTime;


}
