package com.example.finalproject.service;

import com.example.finalproject.dto.PathRequest;
import com.example.finalproject.dto.ReportResponse;
import com.example.finalproject.dto.mapper.ReportMapper;
import com.example.finalproject.model.Report;
import com.example.finalproject.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PathService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private LineString parseWktLineString(String wktLineString) throws ParseException {
        WKTReader wktReader = new WKTReader();
        Geometry geometry = wktReader.read(wktLineString);

        if (geometry instanceof LineString) {
            geometry.setSRID(4326);
            return (LineString) geometry;
        } else {
            throw new IllegalArgumentException("The WKT does not represent a LineString.");
        }
    }

    public List<ReportResponse> getPathReports (PathRequest pathRequest) throws ParseException {
        LineString lineString = parseWktLineString(pathRequest.getPath());
        Date currentDate = new Date();
        List<Report> aliveReports = reportRepository.findAliveAndApprovedReportsWithinLineString(lineString,currentDate);
        List<ReportResponse> realResult = new ArrayList<>();
        for (Report report : aliveReports) {
            ReportResponse reportResponse = reportMapper.convertReportToReportResponse(report);
            realResult.add(reportResponse);
        }
        return realResult;
    }
}
