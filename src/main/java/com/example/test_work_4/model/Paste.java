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
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paste paste = (Paste) o;
        return Objects.equals(url, paste.url) && Objects.equals(title, paste.title) && Objects.equals(content, paste.content) && Objects.equals(created, paste.created) && Objects.equals(expiration, paste.expiration) && access == paste.access && expirationTime == paste.expirationTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, content, created, expiration, access, expirationTime);
    }
}
