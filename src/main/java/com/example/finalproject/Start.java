package com.example.finalproject;

import com.example.finalproject.model.ReportType;
import com.example.finalproject.model.Role;
import com.example.finalproject.model.User;
import com.example.finalproject.model.reports.traffic.TrafficReport;
import com.example.finalproject.model.reports.traffic.TrafficType;
import com.example.finalproject.repository.TrafficReportRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class Start implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TrafficReportRepository trafficReportRepository;

    @Override
    public void run(String... args) throws Exception {
        var user = User.builder()
                .name("Agha Reza")
                .email("reza@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        TrafficReport traffic = new TrafficReport();

        traffic.setTrafficType(TrafficType.Medium);

        traffic.setReportType(ReportType.Traffic);

        double latitude = 40.7128;
        double longitude = -74.0060;
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(longitude, latitude);
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);
        traffic.setLocation(point);

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, 10);
        Date futureDate = calendar.getTime();
        traffic.setExpirationDate(futureDate);

        traffic.setIssueDate(now);
        traffic.setApprove(false);
        traffic.setUser(user);
        trafficReportRepository.save(traffic);

    }
}
