package com.example.finalproject.repository;

import com.example.finalproject.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAll();
    List<Report> findByExpirationDateAfter (Date currentDate);
}
