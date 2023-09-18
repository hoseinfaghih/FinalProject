package com.example.finalproject.repository.reports;

import com.example.finalproject.model.reports.camera.CameraReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraReportRepository extends JpaRepository<CameraReport,Long> {
}
