package com.example.finalproject.controller;

import com.example.finalproject.model.User;
import com.example.finalproject.service.LikeDislikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class LikeDislikeController {
    private final LikeDislikeService likeDislikeService;

    @PostMapping("/like/{id}")
    public ResponseEntity<Object> likeReport(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer check = likeDislikeService.likeReport(id, 1, user);
        switch (check) {
            case 0:
                return ResponseEntity.ok("didn't find such report");
            case 1:
                return ResponseEntity.ok("You already Liked this report");
            case 2:
                return ResponseEntity.ok("You Disliked it before, we removed that and added a like");
            case 3:
                return ResponseEntity.ok("thank you for your like");
        }
        return ResponseEntity.ok("Something Wrong happened!");
    }

    @PostMapping("/dislike/{id}")
    public ResponseEntity<Object> dislikeReport(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer check = likeDislikeService.dislikeReport(id, 1, user);
        switch (check) {
            case 0:
                return ResponseEntity.ok("didn't find such report");
            case 1:
                return ResponseEntity.ok("You already disLiked this report");
            case 2:
                return ResponseEntity.ok("You Liked it before, we removed that and added a dislike");
            case 3:
                return ResponseEntity.ok("thank you for your like");
        }
        return ResponseEntity.ok("Something Wrong happened!");
    }
}
