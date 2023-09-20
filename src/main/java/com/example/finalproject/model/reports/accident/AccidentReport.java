package com.example.finalproject.model.reports.accident;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accident_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccidentReport extends Report {
    @Column(name = "accident-type")
    @Enumerated(EnumType.STRING)
    AccidentType accidentType;
}
