package com.example.finalproject.controller;

import com.example.finalproject.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;
    @GetMapping("/ms-hour") // Most Dangerous Hour
    public ResponseEntity<String> getMostDangerousHour (){
        Integer result = accidentService.getMostDangerousHour();
        if (result == null){
            return ResponseEntity.notFound().build();
        }
        Integer endofResult = result + 1;
        return ResponseEntity.ok("most dangerous hour is between : " + result + " and " + endofResult);
    }
}
