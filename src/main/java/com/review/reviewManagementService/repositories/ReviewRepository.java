package com.review.reviewManagementService.repositories;

import com.review.reviewManagementService.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    Optional<Review> findById(String Id);

    List<Review> findAllByServiceId(Long serviceId);

    Optional<Review> findByUserIdAndServiceId(Long userId, Long serviceId);

    void deleteById(String Id);
}
