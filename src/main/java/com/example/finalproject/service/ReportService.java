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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        calendar.add(Calendar.MINUTE, 10);
        Date futureDate = calendar.getTime();

        Report report = reportMapper.createReport(user, type, innerType, point, now, futureDate);
        if (report != null) {
            reportRepository.save(report);
            return true;
        }
        return false;
    }



    public List<ReportResponse> getAllReports() {
        List<Report> result = reportRepository.findAll();
        List<ReportResponse> realResult = new ArrayList<>();
        for (Report report:result) {
            ReportResponse reportResponse = reportMapper.convertReportToReportResponse(report);
            realResult.add(reportResponse);
        }
        return realResult;
    }

    public List<ReportResponse> getAllAliveReports (){
        Date currentDate = new Date();
        List<Report> aliveReports = reportRepository.findByExpirationDateAfter(currentDate);
        List<ReportResponse> realResult = new ArrayList<>();
        for (Report report:aliveReports) {
            ReportResponse reportResponse = reportMapper.convertReportToReportResponse(report);
            realResult.add(reportResponse);
        }
        return realResult;
    }
    public double checkCamera (Long id){
        return cameraReportRepository.findById(id).get().getLocation().getX();
    }
}
