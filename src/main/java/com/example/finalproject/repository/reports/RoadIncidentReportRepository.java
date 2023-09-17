package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.roadincident.RoadIncidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadIncidentReportRepository extends JpaRepository<RoadIncidentReport,Long> {
}
