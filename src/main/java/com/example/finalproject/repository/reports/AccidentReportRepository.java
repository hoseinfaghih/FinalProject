package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.accident.AccidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccidentReportRepository extends JpaRepository<AccidentReport, Long> {
    Optional<AccidentReport> findById(Long id);

    @Query(value = "SELECT EXTRACT(HOUR FROM issue_date) AS hour_of_day " +
            "FROM accident_reports " +
            "WHERE is_approved = false " +
            "GROUP BY hour_of_day " +
            "ORDER BY COUNT(*) " +
            "DESC LIMIT 1", nativeQuery = true)
    Integer findHourWithMostAccidents();

}
