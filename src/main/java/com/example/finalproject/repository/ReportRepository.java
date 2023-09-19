package com.example.finalproject.repository;

import com.example.finalproject.model.Report;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAll();

    List<Report> findByExpirationDateAfter(Date currentDate);

    Optional<Report> findById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Report r SET r.approve = true WHERE r.id = :reportId")
    void approveReportById(Long reportId);

    @Transactional
    @Modifying
    @Query("UPDATE Report r SET r.approve = false WHERE r.id = :reportId")
    void disapproveReportById(Long reportId);
}
