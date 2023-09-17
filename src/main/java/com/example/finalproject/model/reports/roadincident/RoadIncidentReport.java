package com.example.finalproject.model.reports.roadincident;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roadincident_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoadIncidentReport extends Report {
    @Column(name = "roadincident-type")
    @Enumerated(EnumType.STRING)
    RoadIncidentType roadIncidentType;
}
