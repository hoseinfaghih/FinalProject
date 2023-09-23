package com.example.finalproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "report")
public class ReportProperties {
    private Map<String, Integer> expiration;
    private Map<String, Boolean> approve;

}
