package com.example.test_work_4.repository;

import com.example.test_work_4.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasteRepository extends JpaRepository<Paste, String> {
    List<Paste> findByAccess(String access);
}
