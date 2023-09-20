package com.example.finalproject.model.reports.speedhump;

import com.example.finalproject.model.Report;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Table(name = "speedhump_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpeedHumpReport extends Report {

}
