package com.example.finalproject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo/admin")
public class DemoController {

    @GetMapping("/1")
    public String ok() {
        return "OK";
    }
}
