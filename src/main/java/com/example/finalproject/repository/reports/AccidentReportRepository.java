package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.accident.AccidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentReportRepository extends JpaRepository<AccidentReport,Long> {
}
