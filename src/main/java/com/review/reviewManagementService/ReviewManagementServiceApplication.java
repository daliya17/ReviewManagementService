package com.review.reviewManagementService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReviewManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewManagementServiceApplication.class, args);
    }

}
