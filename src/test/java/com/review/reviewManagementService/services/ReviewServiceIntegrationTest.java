package com.review.reviewManagementService.services;

import com.review.reviewManagementService.dtos.CreateReviewRequestDto;
import com.review.reviewManagementService.models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureDataMongo
@DataMongoTest
@Import({ReviewService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReviewServiceIntegrationTest {


    @Autowired
    private ReviewService reviewService;

   // private ReviewRepository reviewRepository;

    private CreateReviewRequestDto createReviewRequestDto;

    @BeforeEach
    void Setup() {
        createReviewRequestDto = CreateReviewRequestDto.builder()
                .comment("Test Comment").rating(1)
                .serviceId(1L)
                .userId(1L)
                .build();
    }

    @Test
    public void testSaveAndRetrieveRetrieve() throws Exception {
        Review savedReview = reviewService.createReview(createReviewRequestDto);
        Review retrievedReview = reviewService.getReview(savedReview.getId());
        assertNotNull(retrievedReview);
        assertEquals(savedReview.getUserId(), retrievedReview.getUserId());
        assertEquals(savedReview.getServiceId(), retrievedReview.getServiceId());
    }

    @Test
    public void testUpdateReview() throws Exception {
        Review savedReview = reviewService.createReview(createReviewRequestDto);

        // Update the review
        savedReview.setComment("Updated comment");
        savedReview.setRating(4);

        Review updatedReview = reviewService.updateReview(savedReview.getId(), savedReview);
        Review retrievedReview = reviewService.getReview(savedReview.getId());
        assertNotNull(retrievedReview);

        // Verify the updated details
        assertEquals(updatedReview.getUserId(), retrievedReview.getUserId());
        assertEquals(updatedReview.getServiceId(), retrievedReview.getServiceId());
        assertEquals(updatedReview.getComment(), retrievedReview.getComment());
        assertEquals(updatedReview.getRating(), retrievedReview.getRating());
    }

    @Test
    public void testDeleteReview() throws Exception {
        Review savedReview = reviewService.createReview(createReviewRequestDto);
        reviewService.deleteReview(savedReview.getId());
        assertThrows(Exception.class, () -> {
            reviewService.getReview(savedReview.getId());
        });
    }

}