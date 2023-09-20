package com.example.finalproject.model.reports.mapproblem;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mapproblem_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapProblemReport extends Report {
    @Column(name = "mapproblem-type")
    @Enumerated(EnumType.STRING)
    MapProblemType mapProblemType;
}
