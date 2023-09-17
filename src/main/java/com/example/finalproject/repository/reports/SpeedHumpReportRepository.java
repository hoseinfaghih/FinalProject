package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.speedhump.SpeedHumpReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeedHumpReportRepository extends JpaRepository<SpeedHumpReport,Long> {
}
