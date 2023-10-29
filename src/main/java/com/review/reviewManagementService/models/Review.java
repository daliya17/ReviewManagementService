package com.review.reviewManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private Long userId;

    private String serviceName;

    private Long serviceId;

    private int rating;

    private String comment;

    private int likes;

    private Set<User> likedByUserIds = new HashSet<>();

    public void addLike(User user) {
        if (!likedByUserIds.contains(user)) {
            likedByUserIds.add(user);
            likes++;
        } else {
            unlike(user);
        }
    }

    public void unlike(User user) {
        if (likedByUserIds.contains(user)) {
            likedByUserIds.remove(user);
            likes--;
        }
    }
}
