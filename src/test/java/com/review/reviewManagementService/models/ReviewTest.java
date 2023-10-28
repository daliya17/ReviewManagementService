package com.review.reviewManagementService.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewTest {

    @InjectMocks
    private Review review;

    private User user;

    @BeforeEach
    void SetUp() {
        user = User.builder().build();
        review = Review.builder().rating(1).likes(1)
                .likedByUserIds(new HashSet<>()).build();
    }

    @Test
    public void testAddLike() {
        review.addLike(user);
        assertTrue(review.getLikedByUserIds().contains(user));
        assertEquals(2, review.getLikes());
    }

    @Test
    public void testAddLikeByExistingUser() {
        review.getLikedByUserIds().add(user);
        assertEquals(1, review.getLikes());
    }

    @Test
    public void testUnlike() {
        review.getLikedByUserIds().add(user);
        review.unlike(user);
        assertTrue(review.getLikes() == 0);
        assertEquals(0, review.getLikedByUserIds().size());
    }
}