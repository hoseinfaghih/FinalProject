package com.example.finalproject.controller;

import com.example.finalproject.dto.ApproveList;
import com.example.finalproject.dto.ReportResponse;
import com.example.finalproject.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ReportService reportService;

    @PostMapping("/approve")
    public ResponseEntity<Object> approveReports(@RequestBody ApproveList approveList) {
        List<Long> reportIds = approveList.getIds();
        String result = reportService.approveReports(reportIds);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/disapprove")
    public ResponseEntity<Object> disapproveReports(@RequestBody ApproveList approveList) {
        List<Long> reportIds = approveList.getIds();
        String result = reportService.disapproveReports(reportIds);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        List<ReportResponse> result = reportService.getAllReports();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all-alive")
    public ResponseEntity<List<ReportResponse>> getAllAliveReports() {
        return ResponseEntity.ok(reportService.getAllAliveReports());
    }
}
