package com.example.finalproject.repository;

import com.example.finalproject.model.Report;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAll(); // approve == null && alive == null

    List<Report> findByExpirationDateAfter(Date currentDate); // approve == null && alive == true

    List<Report> findByExpirationDateBefore(Date currentDate); // approve == null && alive == false

    List<Report> findByApproveTrue(); // approve == true && alive == null

    List<Report> findByApproveFalse();// approve == false && alive == null

    List<Report> findByApproveFalseAndExpirationDateBefore(Date currentDate); // approve == false && alive == false

    List<Report> findByApproveFalseAndExpirationDateAfter(Date currentDate); // approve == false && alive == true

    List<Report> findByApproveTrueAndExpirationDateBefore (Date currentDate); // approve == true && alive == false

    List<Report> findByApproveTrueAndExpirationDateAfter(Date currentDate);


    Optional<Report> findById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Report r SET r.approve = true WHERE r.id = :reportId")
    void approveReportById(Long reportId);

    @Transactional
    @Modifying
    @Query("UPDATE Report r SET r.approve = false WHERE r.id = :reportId")
    void disapproveReportById(Long reportId);

    @Query("SELECT r FROM Report r WHERE ST_DWithin(r.location, :lineString,10,true) = true  AND r.approve = true AND r.expirationDate > :currentDate")
    List<Report> findAliveAndApprovedReportsWithinLineString(LineString lineString,Date currentDate);
}
