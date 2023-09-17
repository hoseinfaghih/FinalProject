package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.mapproblem.MapProblemReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapProblemReportRepository extends JpaRepository<MapProblemReport,Long> {
}
