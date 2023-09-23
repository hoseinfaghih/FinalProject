package com.example.finalproject.repository;

import com.example.finalproject.model.LikeDislike;
import com.example.finalproject.model.Report;
import com.example.finalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeDislikeRepository extends JpaRepository<LikeDislike, Long> {
    Optional<LikeDislike> findByUserAndReportAndLiked(User user, Report report, Boolean like);

    void deleteByUserAndReportAndLiked(User user, Report report, Boolean like);
}
