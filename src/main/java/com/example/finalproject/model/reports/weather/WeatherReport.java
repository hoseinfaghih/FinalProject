package com.example.finalproject.model.reports.weather;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherReport extends Report {
    @Column(name = "weather-type")
    @Enumerated(EnumType.STRING)
    WeatherType weatherType;

}
