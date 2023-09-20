package com.example.finalproject.controller;

import com.example.finalproject.dto.PathRequest;
import com.example.finalproject.dto.ReportResponse;
import com.example.finalproject.service.PathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/path")
@Slf4j
public class PathController {

    private final PathService pathService;

    @GetMapping("/near-reports")
    public ResponseEntity<List<ReportResponse>> getNearReports(@RequestBody PathRequest pathRequest) throws ParseException {
        List<ReportResponse> result = pathService.getPathReports(pathRequest);
        return ResponseEntity.ok(result);
    }
}
