package com.example.finalproject.model.reports.camera;

import com.example.finalproject.model.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "camera_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CameraReport extends Report {
    @Column(name = "camera-type")
    @Enumerated(EnumType.STRING)
    CameraType cameraType;
}
