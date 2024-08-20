package com.firomsa.movieforum.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firomsa.movieforum.model.Review;
import com.firomsa.movieforum.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getReviews() {
        return new ResponseEntity<>(reviewService.findAllReviews(),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable String userId) {
        return new ResponseEntity<>(reviewService.findAllReviewsByUserId(new ObjectId(userId)),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        return new ResponseEntity<>(reviewService.findReview(new ObjectId(id)),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Map<String, String> payload) {
        String imdbId = payload.get("imdbId");
        ObjectId userId = new ObjectId(payload.get("userId"));
        String comment = payload.get("comment");
        int rating = Integer.parseInt(payload.get("rating"));
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        Review review = new Review(userId, comment, rating, created, updated);
        return new ResponseEntity<>(reviewService.addReview(imdbId,review),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody Map<String, String> payload) {
        Review existingReview = reviewService.findReview(new ObjectId(id));
        if (existingReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        existingReview.setUserId(new ObjectId(payload.get("userId")));
        existingReview.setComment(payload.get("comment"));
        existingReview.setRating(Integer.parseInt(payload.get("rating")));
        existingReview.setUpdated(LocalDateTime.now());
        
        Review updatedReview = reviewService.updateReview(existingReview);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable String id){
        Review existingReview = reviewService.findReview(new ObjectId(id));
        if (existingReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int result = reviewService.deleteReview(existingReview);
        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
