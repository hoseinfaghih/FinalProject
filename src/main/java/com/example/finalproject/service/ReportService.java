package com.example.finalproject.service;

import com.example.finalproject.dto.ReportResponse;
import com.example.finalproject.dto.SubmitReportDto;
import com.example.finalproject.dto.mapper.ReportMapper;
import com.example.finalproject.model.Report;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.ReportRepository;
import com.example.finalproject.repository.reports.*;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final TrafficReportRepository trafficReportRepository;
    private final CameraReportRepository cameraReportRepository;
    private final AccidentReportRepository accidentReportRepository;
    private final PoliceReportRepository policeReportRepository;
    private final SpeedHumpReportRepository speedHumpReportRepository;
    private final MapProblemReportRepository mapProblemReportRepository;
    private final RoadLocationReportRepository roadLocationReportRepository;
    private final RoadIncidentReportRepository roadIncidentReportRepository;
    private final WeatherReportRepository weatherReportRepository;
    private final ReportMapper reportMapper;

    public Boolean addReport(User user, SubmitReportDto submitReportDto) {
        String type = submitReportDto.getType();
        String innerType = submitReportDto.getInnerType();
        double x = submitReportDto.getX();
        double y = submitReportDto.getY();

        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(x, y);
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, 1);
        Date futureDate = calendar.getTime();

        Report report = reportMapper.createReport(user, type, innerType, point, now, futureDate);
        if (report != null) {
            reportRepository.save(report);
            return true;
        }
        return false;
    }

    public List<ReportResponse> getFilteredReports(Boolean approve, Boolean alive) {
        List<Report> result = new ArrayList<>();
        Date now = new Date();
        if (approve == null && alive == null) {
            result = reportRepository.findAll();
        } else if (approve == null && !alive) {
            result = reportRepository.findByExpirationDateBefore(now);
        } else if (approve == null && alive) {
            result = reportRepository.findByExpirationDateAfter(now);
        } else if (!approve && alive == null) {
            result = reportRepository.findByApproveFalse();
        } else if (!approve  && !alive ) {
            result = reportRepository.findByApproveFalseAndExpirationDateBefore(now);
        } else if (!approve  && alive ) {
            result = reportRepository.findByApproveFalseAndExpirationDateAfter(now);
        } else if (approve && alive == null) {
            result = reportRepository.findByApproveTrue();
        } else if (approve  && !alive) {
            result = reportRepository.findByApproveTrueAndExpirationDateBefore(now);
        } else if (approve && alive ) {
            result = reportRepository.findByApproveTrueAndExpirationDateAfter(now);
        }


        List<ReportResponse> realResult = new ArrayList<>();
        for (Report report : result) {
            ReportResponse reportResponse = reportMapper.convertReportToReportResponse(report);
            realResult.add(reportResponse);
        }
        return realResult;
    }

    public List<ReportResponse> getAllAliveReports() {
        Date currentDate = new Date();
        List<Report> aliveReports = reportRepository.findByExpirationDateAfter(currentDate);
        List<ReportResponse> realResult = new ArrayList<>();
        for (Report report : aliveReports) {
            ReportResponse reportResponse = reportMapper.convertReportToReportResponse(report);
            realResult.add(reportResponse);
        }
        return realResult;
    }

    public String approveReports(List<Long> reportIds) {
        Map<String, List<Long>> result = new HashMap<>();
        result.put("good", new ArrayList<>());
        result.put("bad", new ArrayList<>());
        for (Long id : reportIds) {
            Optional<Report> report = reportRepository.findById(id);
            if (report.isPresent()) {
                reportRepository.approveReportById(id);
                result.get("good").add(id);
            } else {
                result.get("bad").add(id);
            }
        }
        return approveReportsResult(result);
    }

    public String disapproveReports(List<Long> reportIds) {
        Map<String, List<Long>> result = new HashMap<>();
        result.put("good", new ArrayList<>());
        result.put("bad", new ArrayList<>());
        for (Long id : reportIds) {
            Optional<Report> report = reportRepository.findById(id);
            if (report.isPresent()) {
                reportRepository.disapproveReportById(id);
                result.get("good").add(id);
            } else {
                result.get("bad").add(id);
            }
        }
        return disapproveReportsResult(result);
    }

    private String approveReportsResult(Map<String, List<Long>> map) {
        StringBuilder responseBody = new StringBuilder("ids : ");
        for (Long goodId : map.get("good")) {
            responseBody.append(goodId.toString()).append(",");
        }
        responseBody.append(" \nwere good and approved!\nbut ids :");
        for (Long goodId : map.get("bad")) {
            responseBody.append(goodId.toString()).append(",");
        }
        responseBody.append(" \nwere bad and didn't exist");
        return responseBody.toString();
    }

    private String disapproveReportsResult(Map<String, List<Long>> map) {
        StringBuilder responseBody = new StringBuilder("ids : ");
        for (Long goodId : map.get("good")) {
            responseBody.append(goodId.toString()).append(",");
        }
        responseBody.append(" \nwere good and disapproved!\nbut ids :");
        for (Long goodId : map.get("bad")) {
            responseBody.append(goodId.toString()).append(",");
        }
        responseBody.append(" \nwere bad and didn't exist");
        return responseBody.toString();
    }
}
