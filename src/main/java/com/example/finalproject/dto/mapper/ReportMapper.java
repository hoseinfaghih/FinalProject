package com.example.finalproject.dto.mapper;

import com.example.finalproject.dto.ReportResponse;
import com.example.finalproject.model.Report;
import com.example.finalproject.model.ReportType;
import com.example.finalproject.model.User;
import com.example.finalproject.model.reports.accident.AccidentReport;
import com.example.finalproject.model.reports.accident.AccidentType;
import com.example.finalproject.model.reports.camera.CameraReport;
import com.example.finalproject.model.reports.camera.CameraType;
import com.example.finalproject.model.reports.mapproblem.MapProblemReport;
import com.example.finalproject.model.reports.mapproblem.MapProblemType;
import com.example.finalproject.model.reports.police.PoliceReport;
import com.example.finalproject.model.reports.police.PoliceType;
import com.example.finalproject.model.reports.roadincident.RoadIncidentReport;
import com.example.finalproject.model.reports.roadincident.RoadIncidentType;
import com.example.finalproject.model.reports.roadlocation.RoadLocationReport;
import com.example.finalproject.model.reports.roadlocation.RoadLocationType;
import com.example.finalproject.model.reports.speedhump.SpeedHumpReport;
import com.example.finalproject.model.reports.traffic.TrafficReport;
import com.example.finalproject.model.reports.traffic.TrafficType;
import com.example.finalproject.model.reports.weather.WeatherReport;
import com.example.finalproject.model.reports.weather.WeatherType;
import com.example.finalproject.repository.reports.*;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ReportMapper {
    private final TrafficReportRepository trafficReportRepository;
    private final CameraReportRepository cameraReportRepository;
    private final AccidentReportRepository accidentReportRepository;
    private final PoliceReportRepository policeReportRepository;
    private final SpeedHumpReportRepository speedHumpReportRepository;
    private final MapProblemReportRepository mapProblemReportRepository;
    private final RoadLocationReportRepository roadLocationReportRepository;
    private final RoadIncidentReportRepository roadIncidentReportRepository;
    private final WeatherReportRepository weatherReportRepository;

    public ReportResponse convertReportToReportResponse(Report report) {
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setId(report.getId());
        reportResponse.setIssueDate(report.getIssueDate());
        reportResponse.setExpirationDate(report.getExpirationDate());
        reportResponse.setType(report.getReportType().name());
        reportResponse.setReporterEmail(report.getUser().getEmail());
        reportResponse.setApproved(report.getApprove());
        reportResponse.setX(report.getLocation().getX());
        reportResponse.setY(report.getLocation().getY());

        switch (report.getReportType()) {
            case Camera:
                reportResponse.setInnerType(cameraReportRepository.findById(report.getId()).get().getCameraType().name());
                break;
            case Accident:
                reportResponse.setInnerType(accidentReportRepository.findById(report.getId()).get().getAccidentType().name());
                break;
            case Traffic:
                reportResponse.setInnerType(trafficReportRepository.findById(report.getId()).get().getTrafficType().name());
                break;
            case Police:
                reportResponse.setInnerType(policeReportRepository.findById(report.getId()).get().getPoliceType().name());
                break;
            case SpeedHump:
                reportResponse.setInnerType(null);
                break;
            case MapProblem:
                reportResponse.setInnerType(mapProblemReportRepository.findById(report.getId()).get().getMapProblemType().name());
                break;
            case RoadLocation:
                reportResponse.setInnerType(roadLocationReportRepository.findById(report.getId()).get().getRoadLocationType().name());
                break;
            case RoadIncident:
                reportResponse.setInnerType(roadIncidentReportRepository.findById(report.getId()).get().getRoadIncidentType().name());
                break;
            case Weather:
                reportResponse.setInnerType(weatherReportRepository.findById(report.getId()).get().getWeatherType().name());
                break;
        }

        return reportResponse;
    }

    public Report createReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        return switch (type) {
            case "Traffic" -> createTrafficReport(user, type, innerType, point, now, futureDate,approve);
            case "Camera" -> createCameraReport(user, type, innerType, point, now, futureDate,approve);
            case "Accident" -> createAccidentReport(user, type, innerType, point, now, futureDate,approve);
            case "Police" -> createPoliceReport(user, type, innerType, point, now, futureDate,approve);
            case "SpeedHump" -> createSpeedHumpReport(user, type, point, now, futureDate,approve);
            case "MapProblem" -> createMapProblemReport(user, type, innerType, point, now, futureDate,approve);
            case "RoadIncident" -> createRoadIncidentReport(user, type, innerType, point, now, futureDate,approve);
            case "RoadLocation" -> createRoadLocationReport(user, type, innerType, point, now, futureDate,approve);
            case "Weather" -> createWeatherReport(user, type, innerType, point, now, futureDate,approve);
            default -> null;
        };
    }

    private TrafficReport createTrafficReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        TrafficReport trafficReport = new TrafficReport();
        setCommonAttributes(trafficReport, user, type, point, now, futureDate,approve);
        trafficReport.setTrafficType(TrafficType.valueOf(innerType));
        return trafficReport;
    }

    private CameraReport createCameraReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        CameraReport cameraReport = new CameraReport();
        setCommonAttributes(cameraReport, user, type, point, now, futureDate,approve);
        cameraReport.setCameraType(CameraType.valueOf(innerType));
        return cameraReport;
    }

    private AccidentReport createAccidentReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        AccidentReport accidentReport = new AccidentReport();
        setCommonAttributes(accidentReport, user, type, point, now, futureDate,approve);
        accidentReport.setAccidentType(AccidentType.valueOf(innerType));
        return accidentReport;
    }

    private PoliceReport createPoliceReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        PoliceReport policeReport = new PoliceReport();
        setCommonAttributes(policeReport, user, type, point, now, futureDate,approve);
        policeReport.setPoliceType(PoliceType.valueOf(innerType));
        return policeReport;
    }

    private SpeedHumpReport createSpeedHumpReport(User user, String type, Point point, Date now, Date futureDate,Boolean approve) {
        SpeedHumpReport speedHumpReport = new SpeedHumpReport();
        setCommonAttributes(speedHumpReport, user, type, point, now, futureDate,approve);
        return speedHumpReport;
    }

    private MapProblemReport createMapProblemReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        MapProblemReport mapProblemReport = new MapProblemReport();
        setCommonAttributes(mapProblemReport, user, type, point, now, futureDate,approve);
        mapProblemReport.setMapProblemType(MapProblemType.valueOf(innerType));
        return mapProblemReport;
    }

    private RoadIncidentReport createRoadIncidentReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        RoadIncidentReport roadIncidentReport = new RoadIncidentReport();
        setCommonAttributes(roadIncidentReport, user, type, point, now, futureDate,approve);
        roadIncidentReport.setRoadIncidentType(RoadIncidentType.valueOf(innerType));
        return roadIncidentReport;
    }

    private RoadLocationReport createRoadLocationReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        RoadLocationReport roadLocationReport = new RoadLocationReport();
        setCommonAttributes(roadLocationReport, user, type, point, now, futureDate,approve);
        roadLocationReport.setRoadLocationType(RoadLocationType.valueOf(innerType));
        return roadLocationReport;
    }

    private WeatherReport createWeatherReport(User user, String type, String innerType, Point point, Date now, Date futureDate,Boolean approve) {
        WeatherReport weatherReport = new WeatherReport();
        setCommonAttributes(weatherReport, user, type, point, now, futureDate,approve);
        weatherReport.setWeatherType(WeatherType.valueOf(innerType));
        return weatherReport;
    }

    private void setCommonAttributes(Report report, User user, String type, Point point, Date now, Date futureDate,Boolean approve) {
        report.setApprove(approve);
        report.setLocation(point);
        report.setUser(user);
        report.setIssueDate(now);
        report.setExpirationDate(futureDate);
        report.setReportType(ReportType.valueOf(type));
    }
}