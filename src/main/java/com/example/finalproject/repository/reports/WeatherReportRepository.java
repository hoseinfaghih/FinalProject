package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.weather.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherReportRepository extends JpaRepository<WeatherReport, Long> {
    Optional<WeatherReport> findById(Long id);
}
