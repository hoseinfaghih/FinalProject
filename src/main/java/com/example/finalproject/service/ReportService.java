package com.example.finalproject.service;

import com.example.finalproject.dto.SubmitReportDto;
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
import com.example.finalproject.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

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

        Report report = createReport(user, type, innerType, point, now, futureDate);
        if (report != null) {
            reportRepository.save(report);
            return true;
        }
        return false;
    }

    private Report createReport(User user, String type, String innerType, Point point, Date now, Date futureDate) {
        switch (type) {
            case "Traffic":
                return createTrafficReport(user, type, innerType, point, now, futureDate);
            case "Camera":
                return createCameraReport(user,type, innerType, point, now, futureDate);
            case "Accident":
                return createAccidentReport(user,type, innerType, point, now, futureDate);
            case "Police":
                return createPoliceReport(user,type, innerType, point, now, futureDate);
            case "SpeedHump":
                return createSpeedHumpReport(user,type, point, now, futureDate);
            case "MapProblem":
                return createMapProblemReport(user,type, innerType, point, now, futureDate);
            case "RoadIncident":
                return createRoadIncidentReport(user,type, innerType, point, now, futureDate);
            case "RoadLocation":
                return createRoadLocationReport(user,type, innerType, point, now, futureDate);
            case "Weather":
                return createWeatherReport(user,type, innerType, point, now, futureDate);
            default:
                return null;
        }
    }

    private TrafficReport createTrafficReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        TrafficReport trafficReport = new TrafficReport();
        setCommonAttributes(trafficReport, user,type, point, now, futureDate);
        trafficReport.setTrafficType(TrafficType.valueOf(innerType));
        return trafficReport;
    }

    private CameraReport createCameraReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        CameraReport cameraReport = new CameraReport();
        setCommonAttributes(cameraReport, user,type, point, now, futureDate);
        cameraReport.setCameraType(CameraType.valueOf(innerType));
        return cameraReport;
    }

    private AccidentReport createAccidentReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        AccidentReport accidentReport = new AccidentReport();
        setCommonAttributes(accidentReport, user,type, point, now, futureDate);
        accidentReport.setAccidentType(AccidentType.valueOf(innerType));
        return accidentReport;
    }

    private PoliceReport createPoliceReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        PoliceReport policeReport = new PoliceReport();
        setCommonAttributes(policeReport, user,type, point, now, futureDate);
        policeReport.setPoliceType(PoliceType.valueOf(innerType));
        return policeReport;
    }

    private SpeedHumpReport createSpeedHumpReport(User user,String type, Point point, Date now, Date futureDate) {
        SpeedHumpReport speedHumpReport = new SpeedHumpReport();
        setCommonAttributes(speedHumpReport, user,type, point, now, futureDate);
        return speedHumpReport;
    }

    private MapProblemReport createMapProblemReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        MapProblemReport mapProblemReport = new MapProblemReport();
        setCommonAttributes(mapProblemReport, user,type, point, now, futureDate);
        mapProblemReport.setMapProblemType(MapProblemType.valueOf(innerType));
        return mapProblemReport;
    }

    private RoadIncidentReport createRoadIncidentReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        RoadIncidentReport roadIncidentReport = new RoadIncidentReport();
        setCommonAttributes(roadIncidentReport, user,type, point, now, futureDate);
        roadIncidentReport.setRoadIncidentType(RoadIncidentType.valueOf(innerType));
        return roadIncidentReport;
    }

    private RoadLocationReport createRoadLocationReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        RoadLocationReport roadLocationReport = new RoadLocationReport();
        setCommonAttributes(roadLocationReport, user,type, point, now, futureDate);
        roadLocationReport.setRoadLocationType(RoadLocationType.valueOf(innerType));
        return roadLocationReport;
    }

    private WeatherReport createWeatherReport(User user,String type, String innerType, Point point, Date now, Date futureDate) {
        WeatherReport weatherReport = new WeatherReport();
        setCommonAttributes(weatherReport, user,type, point, now, futureDate);
        weatherReport.setWeatherType(WeatherType.valueOf(innerType));
        return weatherReport;
    }

    private void setCommonAttributes(Report report, User user,String type, Point point, Date now, Date futureDate) {
        report.setApprove(false);
        report.setLocation(point);
        report.setUser(user);
        report.setIssueDate(now);
        report.setExpirationDate(futureDate);
        report.setReportType(ReportType.valueOf(type));
    }
}
