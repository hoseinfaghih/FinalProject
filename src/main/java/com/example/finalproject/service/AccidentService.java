package com.example.finalproject.service;

import com.example.finalproject.repository.reports.AccidentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccidentService {
    private final AccidentReportRepository accidentReportRepository;

    public Integer getMostDangerousHour (){
        return accidentReportRepository.findHourWithMostAccidents();
    }
}
