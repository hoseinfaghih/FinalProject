package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.traffic.TrafficReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficReportRepository extends JpaRepository<TrafficReport, Long> {
    List<TrafficReport> findAll();
}
