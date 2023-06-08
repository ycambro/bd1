package tec.bd.repository;

import java.util.List;
import java.util.Optional;

import tec.bd.entities.Review;

public interface ReviewRepository extends CRUDRepository<Review, Integer> {
    public List<Review> findAll();

    public Optional<Review> findById(Integer entityId);

    public Review save (Review entity);

    public void delete(Integer entityId);

    public Review update(Review entity);
}
