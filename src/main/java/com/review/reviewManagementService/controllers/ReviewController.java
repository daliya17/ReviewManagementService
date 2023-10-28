package com.review.reviewManagementService.controllers;

import com.review.reviewManagementService.dtos.CreateReviewRequestDto;
import com.review.reviewManagementService.models.Review;
import com.review.reviewManagementService.models.User;
import com.review.reviewManagementService.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody CreateReviewRequestDto reviewRequestDto) {
        Review review = reviewService.createReview(reviewRequestDto);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable("id") String id) throws Exception {
        Review review = reviewService.getReview(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/getAllReviews/{serviceId}")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable("serviceId") Long serviceId) {
        return new ResponseEntity<>(reviewService.getAllReviews(serviceId), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") String id, @RequestBody Review review) throws Exception {
        Review updatedReview = reviewService.updateReview(id, review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable("id") String id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{reviewId}/like")
    public ResponseEntity<Review> likeReview(@PathVariable("reviewId") String id, @RequestBody User user) throws Exception {
        Review review = reviewService.likeReview(id, user);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PatchMapping("{reviewId}/unlike")
    public ResponseEntity<Review> unlikeReview(@PathVariable("reviewId") String id, @RequestBody User user) throws Exception {
        Review review = reviewService.unlikeReview(id, user);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

}
