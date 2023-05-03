package com.example.test_work_4.repository;


import com.example.test_work_4.enums.Access;
import com.example.test_work_4.model.Paste;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste, String>, JpaSpecificationExecutor<Paste> {
    List<Paste> findTop10ByAccessAndExpirationAfterOrderByCreatedDesc(Access status, Instant date);
    Optional<Paste> findPasteByUrlAndExpirationAfter(String url, Instant now);
    @Override
    List<Paste> findAll(Specification<Paste> specification);
    void deleteAllByExpirationIsBefore(Instant now);

}

