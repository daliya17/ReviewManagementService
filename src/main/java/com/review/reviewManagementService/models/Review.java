package com.review.reviewManagementService.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private Long userId;

    private String serviceName;

    private Long serviceId;

    private int rating;

    private String comment;

    private int likeCount = 0;
}
