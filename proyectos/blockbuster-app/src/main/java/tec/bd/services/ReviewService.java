package tec.bd.services;

import tec.bd.entities.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> getReview();

    Optional<Review> getReviewById(int reviewId);

    Review newReview(Review review);

    void removeReview(int reviewId);

    Review updateReview(Review review);

}
