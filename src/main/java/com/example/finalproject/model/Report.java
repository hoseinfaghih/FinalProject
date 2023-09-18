package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "reports")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "report_id")
    Long id;

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    ReportType reportType;

    @Column(name = "issue_date")
    Date issueDate;

    @Column(name = "expiration_date")
    Date expirationDate;

    @Column(name = "coordinates", columnDefinition = "geometry(Point,4326)")
    Point location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "is_approved")
    Boolean approve;
}
