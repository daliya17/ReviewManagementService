package com.review.reviewManagementService.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequestDto {

    private Long userId;

    private String serviceName;

    private Long serviceId;

    private int rating;

    private String comment;
}
