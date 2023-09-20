package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.speedhump.SpeedHumpReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeedHumpReportRepository extends JpaRepository<SpeedHumpReport, Long> {
    Optional<SpeedHumpReport> findById(Long id);
}
