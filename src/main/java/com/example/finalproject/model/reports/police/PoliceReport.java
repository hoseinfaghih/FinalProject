package com.example.finalproject.model.reports.police;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "police_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PoliceReport extends Report {
    @Column(name = "polic-type")
    @Enumerated(EnumType.STRING)
    PoliceType policeType;
}
