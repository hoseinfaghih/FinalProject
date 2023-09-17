package com.example.finalproject.model.reports.roadlocation;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roadlocation_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoadLocationReport extends Report {
    @Column(name = "roadlocation-reports")
    @Enumerated(EnumType.STRING)
    RoadLocationType roadLocationType;

}
