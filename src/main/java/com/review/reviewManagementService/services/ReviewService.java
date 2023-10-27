package com.review.reviewManagementService.services;

import com.review.reviewManagementService.dtos.CreateReviewRequestDto;
import com.review.reviewManagementService.models.Review;
import com.review.reviewManagementService.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(CreateReviewRequestDto createReviewRequestDto) {

        Optional<Review> existingReview = reviewRepository.findByUserIdAndServiceId(createReviewRequestDto.getUserId(), createReviewRequestDto.getServiceId());
        if (existingReview.isPresent()) {
            existingReview.get().setComment(createReviewRequestDto.getComment());
            existingReview.get().setRating(createReviewRequestDto.getRating());
            return reviewRepository.save(existingReview.get());
        } else {
            Review review = new Review();
            review.setComment(createReviewRequestDto.getComment());
            review.setServiceName(createReviewRequestDto.getServiceName());
            review.setRating(createReviewRequestDto.getRating());
            review.setUserId(createReviewRequestDto.getUserId());
            review.setServiceId(createReviewRequestDto.getServiceId());
            return reviewRepository.save(review);
        }
    }

    public Review getReview(String id) throws Exception {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            throw new Exception("Review Not Found");
        }
        return review.get();
    }

    public Review updateReview(String id, Review reviewRequest) throws Exception {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            throw new Exception("Review Not Found");
        }
        review.get().setUserId(reviewRequest.getUserId());
        review.get().setServiceName(reviewRequest.getServiceName());
        review.get().setRating(reviewRequest.getRating());
        review.get().setComment(reviewRequest.getComment());
        return reviewRepository.save(review.get());
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    public Review likeReview(String id) throws Exception {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            throw new Exception("Review Not Found");
        }
        review.get().setLikeCount(review.get().getLikeCount() + 1);
        return reviewRepository.save(review.get());
    }

    public Review dislikeReview(String id) throws Exception {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            throw new Exception("Review Not Found");
        }
        review.get().setLikeCount(review.get().getLikeCount() - 1);
        return reviewRepository.save(review.get());

    }

    public List<Review> getAllReviews(Long serviceId) {
        List<Review> reviews = reviewRepository.findAllByServiceId(serviceId);
        return reviews;
    }
}
