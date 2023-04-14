package com.example.test_work_4.repository;

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
    @Query(value = "select * from paste p where p.access in ('PUBLIC') order by created desc limit 10", nativeQuery = true)
    List<Paste> findAllByStatusPublic();
    Optional<Paste> findPasteByUrl(String url);
    @Override
    List<Paste> findAll(Specification<Paste> specification);
    void deleteAllByExpirationIsBefore(Instant now);
}

