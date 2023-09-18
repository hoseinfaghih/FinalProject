package com.example.finalproject.controller;

import com.example.finalproject.dto.SubmitReportDto;
import com.example.finalproject.model.User;
import com.example.finalproject.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/add-report")
    public ResponseEntity<Object> addReport(@RequestBody SubmitReportDto submitReportDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if ((submitReportDto.getX() > 180 || submitReportDto.getX() < -180) ||
                (submitReportDto.getY() < -90 || submitReportDto.getY() > 90)) {
            return ResponseEntity.badRequest().body("Bad coordinates !!");
        }
        if (submitReportDto.getType() == null){
            return ResponseEntity.badRequest().body("no Type !");
        }
        if (!submitReportDto.getType().equals("SpeedHump") && submitReportDto.getInnerType()==null){
            return ResponseEntity.badRequest().body("no InnerType !!");
        }
        Boolean result = reportService.addReport(user, submitReportDto);
        if (result){
            return ResponseEntity.ok("Thank you for you Report!");
        }else {
            return ResponseEntity.badRequest().body("something wrong happened!");
        }
    }
}
