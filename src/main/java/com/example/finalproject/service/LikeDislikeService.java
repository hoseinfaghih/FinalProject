package com.example.finalproject.service;

import com.example.finalproject.model.LikeDislike;
import com.example.finalproject.model.Report;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.LikeDislikeRepository;
import com.example.finalproject.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeDislikeService {
    private final ReportRepository reportRepository;
    private final LikeDislikeRepository likeDislikeRepository;
    private final Calendar calendar;

    @Transactional
    public Integer likeReport(Long reportId, int minutes, User user) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (!optionalReport.isPresent()) {
            return 0;
        }
        Report report = optionalReport.get();
        Optional<LikeDislike> existingLike = likeDislikeRepository.findByUserAndReportAndLiked(user, report, true);
        if (existingLike.isPresent()) {
            return 1;
        }
        Optional<LikeDislike> existingDislike = likeDislikeRepository.findByUserAndReportAndLiked(user, report, false);
        if (existingDislike.isPresent()) {
            likeDislikeRepository.delete(existingDislike.get());
            LikeDislike likeDislike = LikeDislike.builder()
                    .user(user)
                    .report(report)
                    .liked(true)
                    .build();
            likeDislikeRepository.save(likeDislike);
            report.setDislikeCount(report.getDislikeCount() - 1);
            report.setLikeCount(report.getLikeCount() + 1);
            calendar.clear();
            calendar.setTime(report.getExpirationDate());
            calendar.add(Calendar.MINUTE, 2 * minutes);
            report.setExpirationDate(calendar.getTime());

            return 2;
        }
        LikeDislike likeDislike = LikeDislike.builder()
                .user(user)
                .report(report)
                .liked(true)
                .build();
        likeDislikeRepository.save(likeDislike);
        report.setLikeCount(report.getLikeCount() + 1);
        calendar.clear();
        calendar.setTime(report.getExpirationDate());
        calendar.add(Calendar.MINUTE, minutes);
        report.setExpirationDate(calendar.getTime());
        return 3;
    }

    @Transactional
    public Integer dislikeReport(Long reportId, int minutes, User user) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (!optionalReport.isPresent()) {
            return 0;
        }
        Report report = optionalReport.get();
        Optional<LikeDislike> existingDislike = likeDislikeRepository.findByUserAndReportAndLiked(user, report, false);
        if (existingDislike.isPresent()) {
            return 1;
        }
        Optional<LikeDislike> existingLike = likeDislikeRepository.findByUserAndReportAndLiked(user, report, true);
        if (existingLike.isPresent()) {
            likeDislikeRepository.delete(existingLike.get());
            LikeDislike likeDislike = LikeDislike.builder()
                    .user(user)
                    .report(report)
                    .liked(false)
                    .build();
            likeDislikeRepository.save(likeDislike);
            report.setDislikeCount(report.getDislikeCount() + 1);
            report.setLikeCount(report.getLikeCount() - 1);
            calendar.clear();
            calendar.setTime(report.getExpirationDate());
            calendar.add(Calendar.MINUTE, -2 * minutes);
            report.setExpirationDate(calendar.getTime());
            return 2;
        }

        LikeDislike likeDislike = LikeDislike.builder()
                .user(user)
                .report(report)
                .liked(false)
                .build();
        likeDislikeRepository.save(likeDislike);
        report.setDislikeCount(report.getDislikeCount() + 1);
        calendar.clear();
        calendar.setTime(report.getExpirationDate());
        calendar.add(Calendar.MINUTE, -1 * minutes);
        report.setExpirationDate(calendar.getTime());
        return 3;

    }
}
