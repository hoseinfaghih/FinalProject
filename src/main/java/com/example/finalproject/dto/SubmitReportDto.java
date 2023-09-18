package com.example.finalproject.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmitReportDto {
    double x;
    double y;
    String type;
    String innerType;
}
