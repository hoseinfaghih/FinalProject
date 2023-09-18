package com.example.finalproject.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {
    Long id;
    Date issueDate;
    Date expirationDate;
    String type;
    String innerType;
    String reporterEmail;
    Boolean approved;
    double x;
    double y;

}
