package com.example.finalproject.service;

import com.example.finalproject.config.RedisConfig;
import com.example.finalproject.config.ReportProperties;
import com.example.finalproject.dto.ReportResponse;
import com.example.finalproject.dto.SubmitReportDto;
import com.example.finalproject.dto.mapper.ReportMapper;
import com.example.finalproject.model.Report;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.ReportRepository;
import com.example.finalproject.repository.reports.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
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
    private final RedisConfig redisConfig;
    private final Calendar calendar;
    private final GeometryFactory geometryFactory;
    private final ReportProperties reportProperties;

    public Boolean addReport(User user, SubmitReportDto submitReportDto) {
        String uniqueIdentifier = submitReportDto.toString().replaceAll("\\s", "");
        RedissonClient redissonClient = redisConfig.redissonClient();
//        RLock lock = redissonClient.getLock(uniqueIdentifier);
//        try{
//            lock.lock();
//            RMap<String, String> map = redissonClient.getMap(uniqueIdentifier);
//            map.put(uniqueIdentifier, "YES");
//            map.expire(2, TimeUnit.MINUTES);
//        }finally {
//            lock.unlock();
//        }

        RMap<String, String> map = redissonClient.getMap(uniqueIdentifier);
        map.put(uniqueIdentifier, "YES");
        map.expire(2, TimeUnit.MINUTES);


        String type = submitReportDto.getType();
        String innerType = submitReportDto.getInnerType();
        double x = submitReportDto.getX();
        double y = submitReportDto.getY();

        Coordinate coordinate = new Coordinate(x, y);
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);
        calendar.clear();
        calendar.setTime(new Date());
        Date now = calendar.getTime();
        Boolean approve = null;
        switch (type) {
            case "Traffic":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("traffic"));
                approve = reportProperties.getApprove().get("traffic");
                break;
            case "Accident":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("accident"));
                approve = reportProperties.getApprove().get("accident");
                break;
            case "Camera":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("camera"));
                approve = reportProperties.getApprove().get("camera");
                break;
            case "MapProblem":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("map-problem"));
                approve = reportProperties.getApprove().get("map-problem");
                break;
            case "Police":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("police"));
                approve = reportProperties.getApprove().get("police");
                break;
            case "RoadIncident":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("road-incident"));
                approve = reportProperties.getApprove().get("road-incident");
                break;
            case "RoadLocation":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("road-location"));
                approve = reportProperties.getApprove().get("road-location");
                break;
            case "SpeedHump":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("speed-hump"));
                approve = reportProperties.getApprove().get("speed-hump");
                break;
            case "Weather":
                calendar.add(Calendar.MINUTE, reportProperties.getExpiration().get("weather"));
                approve = reportProperties.getApprove().get("weather");
                break;
        }


        Date futureDate = calendar.getTime();

        Report report = reportMapper.createReport(user, type, innerType, point, now, futureDate, approve);
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
        } else if (!approve && !alive) {
            result = reportRepository.findByApproveFalseAndExpirationDateBefore(now);
        } else if (!approve && alive) {
            result = reportRepository.findByApproveFalseAndExpirationDateAfter(now);
        } else if (approve && alive == null) {
            result = reportRepository.findByApproveTrue();
        } else if (approve && !alive) {
            result = reportRepository.findByApproveTrueAndExpirationDateBefore(now);
        } else if (approve && alive) {
            result = reportRepository.findByApproveTrueAndExpirationDateAfter(now);
        }


        List<ReportResponse> realResult = new ArrayList<>();
        for (Report report : result) {
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


    public boolean checkDuplicate(SubmitReportDto submitReportDto) {
        String uniqueIdentifier = submitReportDto.toString().replaceAll("\\s", "");
        RedissonClient redissonClient = redisConfig.redissonClient();
//        RLock lock = redissonClient.getLock(uniqueIdentifier);
        boolean result = false;
//        try {
//            lock.lock();
//            RMap<String, String> map = redissonClient.getMap(uniqueIdentifier);
//            if (map.keySet().contains(uniqueIdentifier)) {
//                result = true;
//            }
//        }finally {
//            lock.unlock();
//        }

        RMap<String, String> map = redissonClient.getMap(uniqueIdentifier);
        if (map.containsKey(uniqueIdentifier)) {
            result = true;
        }
        return result;
    }
}
