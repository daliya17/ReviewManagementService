package com.review.reviewManagementService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.reviewManagementService.dtos.CreateReviewRequestDto;
import com.review.reviewManagementService.models.Review;
import com.review.reviewManagementService.models.User;
import com.review.reviewManagementService.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    private MockMvc mockMvc;

    private Review review;

    @BeforeEach
    void setUp() {
        review = Review.builder().rating(1).likes(1).build();
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    public void createsAReviewTest() throws Exception {
        CreateReviewRequestDto createReviewRequestDto = CreateReviewRequestDto.builder().rating(1).build();
        doReturn(review).when(reviewService).createReview(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createReviewRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void getReviewTest() throws Exception {
        doReturn(review).when(reviewService).getReview(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void getAllReviewsTest() throws Exception {
        doReturn(List.of(review, review)).when(reviewService).getAllReviews(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/getAllReviews/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateReview() throws Exception {
        doReturn(review).when(reviewService).updateReview(any(), any());
        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void deleteReviewTest() throws Exception {
        doNothing().when(reviewService).deleteReview(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/reviews/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void likeReviewTest() throws Exception {
        doReturn(review).when(reviewService).likeReview(any(), any());
        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews/1/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new User())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void unlikeReviewTest() throws Exception {
        doReturn(review).when(reviewService).unlikeReview(any(), any());
        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews/1/unlike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new User())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

}