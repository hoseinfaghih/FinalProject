package com.example.finalproject.model.reports.traffic;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "traffic_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrafficReport extends Report {
    @Column(name = "traffic_type")
    @Enumerated(EnumType.STRING)
    TrafficType trafficType;
}
