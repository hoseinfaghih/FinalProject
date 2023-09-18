package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.roadlocation.RoadLocationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoadLocationReportRepository extends JpaRepository<RoadLocationReport, Long> {
    Optional<RoadLocationReport> findById(Long id);
}
