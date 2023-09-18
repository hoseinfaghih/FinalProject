package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.police.PoliceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PoliceReportRepository extends JpaRepository<PoliceReport, Long> {
    Optional<PoliceReport> findById(Long id);

}
