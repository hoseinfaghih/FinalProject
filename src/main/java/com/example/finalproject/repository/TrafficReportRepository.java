package com.example.finalproject.repository;

import com.example.finalproject.model.reports.traffic.TrafficReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficReportRepository extends JpaRepository<TrafficReport, Long> {
}
