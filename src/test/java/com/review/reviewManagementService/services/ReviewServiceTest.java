package com.review.reviewManagementService.services;

import com.review.reviewManagementService.dtos.CreateReviewRequestDto;
import com.review.reviewManagementService.models.Review;
import com.review.reviewManagementService.models.User;
import com.review.reviewManagementService.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    private Review review;

    private CreateReviewRequestDto createReviewRequestDto;

    private User user;

    @BeforeEach
    void setUp() {
        review = Review.builder()
                .rating(3)
                .comment("Average service")
                .likes(1)
                .likedByUserIds(new HashSet<>())
                .build();
        createReviewRequestDto = CreateReviewRequestDto.builder()
                .rating(3)
                .comment("Average service")
                .build();
        user = User.builder()
                .Id(1L)
                .userName("TestName")
                .build();
    }

    @Test
    public void creatReviewTest() {
        doReturn(Optional.empty()).when(reviewRepository).findByUserIdAndServiceId(any(), any());
        doReturn(review).when(reviewRepository).save(any());

        Review savedReview = reviewService.createReview(createReviewRequestDto);
        assertEquals(createReviewRequestDto.getComment(), savedReview.getComment());
        assertEquals(createReviewRequestDto.getRating(), savedReview.getRating());
    }

    @Test
    public void createReview_ExistingReview_Test() {
        doReturn(Optional.of(review)).when(reviewRepository).findByUserIdAndServiceId(any(), any());
        doReturn(review).when(reviewRepository).save(any());

        Review savedReview = reviewService.createReview(createReviewRequestDto);
        assertEquals(createReviewRequestDto.getComment(), savedReview.getComment());
        assertEquals(createReviewRequestDto.getRating(), savedReview.getRating());
    }

    @Test
    public void getExistingReview() throws Exception {
        doReturn(Optional.of(review)).when(reviewRepository).findById(any());

        Review response = reviewService.getReview("123");
        assertEquals(review.getRating(), response.getRating());
        assertEquals(review.getComment(), response.getComment());
        assertEquals(review.getUserId(), response.getUserId());
    }

    @Test
    public void getReviewThrowsException() {
        doReturn(Optional.empty()).when(reviewRepository).findById(any());
        assertThrows(Exception.class, () -> {
            reviewService.getReview("12");
        });
    }

    @Test
    public void updateReview() throws Exception {
        doReturn(Optional.of(review)).when(reviewRepository).findById(any());
        doReturn(review).when(reviewRepository).save(any());

        Review response = reviewService.updateReview("123", review);
        assertEquals(review.getRating(), response.getRating());
        assertEquals(review.getComment(), response.getComment());
        assertEquals(review.getUserId(), response.getUserId());
    }

    @Test
    public void updateReviewThrowsException() {
        doReturn(Optional.empty()).when(reviewRepository).findById(any());
        assertThrows(Exception.class, () -> {
            reviewService.updateReview("12", review);
        });
    }

    @Test
    public void deleteReview() {
        doNothing().when(reviewRepository).deleteById(any());
        reviewService.deleteReview("12");
        verify(reviewRepository).deleteById(any());
    }

    @Test
    public void likeReviewTest() throws Exception {
        int likes = review.getLikes();
        doReturn(Optional.of(review)).when(reviewRepository).findById(any());
        doReturn(review).when(reviewRepository).save(any());
        Review response = reviewService.likeReview("1", user);
        assertEquals(likes + 1, response.getLikes());
    }

    @Test
    public void likeAlreadyLikedReviewTest() throws Exception {
        review.getLikedByUserIds().add(user);
        int likes = review.getLikes();
        doReturn(Optional.of(review)).when(reviewRepository).findById(any());
        doReturn(review).when(reviewRepository).save(any());

        Review response = reviewService.likeReview("1", user);
        assertEquals(likes-1, response.getLikes());
    }

    @Test
    public void likeReview_DoesNotExist_Test() {
        doReturn(Optional.empty()).when(reviewRepository).findById(any());
        assertThrows(Exception.class, () -> {
            reviewService.likeReview("12", user);
        });
    }

    @Test
    public void unlikeReviewTest() throws Exception {
        int likes = review.getLikes();
        review.getLikedByUserIds().add(user);
        doReturn(Optional.of(review)).when(reviewRepository).findById(any());
        doReturn(review).when(reviewRepository).save(any());
        Review response = reviewService.unlikeReview("1", user);
        assertEquals(likes - 1, response.getLikes());
    }

    @Test
    public void unlikeAlreadyUnLikedReviewTest() throws Exception {
        int likes = review.getLikes();
        doReturn(Optional.of(review)).when(reviewRepository).findById(any());
        doReturn(review).when(reviewRepository).save(any());

        Review response = reviewService.unlikeReview("1", user);
        assertEquals(likes, response.getLikes());
    }

    @Test
    public void unlikeReview_DoesNotExist_Test() {
        doReturn(Optional.empty()).when(reviewRepository).findById(any());
        assertThrows(Exception.class, () -> {
            reviewService.unlikeReview("12", user);
        });
    }

    @Test
    public void getAllReviewsTest() {
        doReturn(List.of(review, review)).when(reviewRepository).findAllByServiceId(any());
        var response = reviewService.getAllReviews(1L);
        assertEquals(2, response.size());
    }
}