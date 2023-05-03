package com.example.test_work_4.repository.specification;

import com.example.test_work_4.enums.Access;
import com.example.test_work_4.model.Paste;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;

public class SpecificationPaste {
    public static Specification<Paste> byTitle(String title) {
        return StringUtils.hasText(title) ? (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("title"), title),
                        criteriaBuilder.equal(root.get("access"), Access.PUBLIC)
                ) : null;
    }

    public static Specification<Paste> byBody(String content) {
        return StringUtils.hasText(content) ? (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.like(root.get("content"), "%" + content + "%"),
                        criteriaBuilder.equal(root.get("access"), Access.PUBLIC)
                ) : null;
    }

    public static Specification<Paste> byNotExpired() {
        return (root, query, cb) -> cb.greaterThan(root.get("expiration"), Instant.now());
    }
}
