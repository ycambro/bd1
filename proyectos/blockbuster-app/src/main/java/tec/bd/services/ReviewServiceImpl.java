package tec.bd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.entities.Review;
import tec.bd.repository.ClientRepository;
import tec.bd.repository.ReviewRepository;
import tec.bd.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ReviewServiceImpl implements ReviewService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository reviewRepository;

    private final ClientRepository clientRepository;
    private final MovieRepository movieRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ClientRepository clientRepository, MovieRepository movieRepository) {
        requireNonNull(reviewRepository);
        requireNonNull(clientRepository);
        requireNonNull(movieRepository);

        this.reviewRepository = reviewRepository;
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Review> getReview() {
        return this.reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(int reviewId) {
        // validacion
        if (reviewId > 0) {
            return this.reviewRepository.findById(reviewId);
        }
        return Optional.empty();
    }

    @Override
    public Review newReview(Review review) {
        requireNonNull(review);
        requireNonNull(review.getClient());
        requireNonNull(review.getMovie());

        if (review.getRating() < 0) {
            throw new RuntimeException("Rating is not valid");
        }
        if (review.getReviewText() == null || review.getReviewText().isEmpty()
                || review.getReviewText().isBlank()) {
            throw new RuntimeException("There is not review text");
        }
        if (review.getCreatedOn() == null) {
            throw new RuntimeException("Created on date is not valid");
        }
        if (review.getClient().getClientId() <= 0) {
            throw new RuntimeException("Client Id " + review.getClient().getClientId() + " is not valid");
        }
        if (review.getMovie().getMovieId() <= 0) {
            throw new RuntimeException("Movie Id " + review.getMovie().getMovieId() + " is not valid");
        }

        LOGGER.debug("Creating new Review via adhoc SQL commands");
        this.clientRepository.findById(review.getClient().getClientId()).orElseThrow();
        this.movieRepository.findById(review.getMovie().getMovieId()).orElseThrow();
        return this.reviewRepository.save(review);
    }

    @Override
    public void removeReview(int reviewId) {
        if (reviewId < 1) {
            LOGGER.error("Review id {} is invalid", reviewId);
            throw new RuntimeException("Review id is invalid");
        }

        var reviewInCatalog = this.reviewRepository.findById(reviewId);

        reviewInCatalog.ifPresentOrElse((r) -> {
            this.reviewRepository.delete(reviewId);
        }, () -> {
            LOGGER.debug("Review id {} doesnt exist in catalog", reviewId);
            new RuntimeException("Review doesnt exists in catalog");
        });
    }

    @Override
    public Review updateReview(Review review) {
        requireNonNull(review);
        requireNonNull(review.getClient());
        requireNonNull(review.getMovie());

        if (review.getRating() < 0) {
            throw new RuntimeException("Rating is not valid");
        }
        if (review.getReviewText() == null || review.getReviewText().isEmpty()
                || review.getReviewText().isBlank()) {
            throw new RuntimeException("There is not review text");
        }
        if (review.getCreatedOn() == null) {
            throw new RuntimeException("Created on date is not valid");
        }
        if (review.getClient().getClientId() <= 0) {
            throw new RuntimeException("Client Id " + review.getClient().getClientId() + " is not valid");
        }
        if (review.getMovie().getMovieId() <= 0) {
            throw new RuntimeException("Movie Id " + review.getMovie().getMovieId() + " is not valid");
        }

        LOGGER.debug("Updating the Review via adhoc SQL commands");
        this.clientRepository.findById(review.getClient().getClientId()).orElseThrow();
        this.movieRepository.findById(review.getMovie().getMovieId()).orElseThrow();
        return this.reviewRepository.update(review);
    }
}
