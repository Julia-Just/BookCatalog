package org.top.authorscatalog.rdb;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Review;
import org.top.authorscatalog.rdb.repository.ReviewRepository;
import org.top.authorscatalog.service.ReviewService;

import java.util.Date;
import java.util.Optional;

@Service
public class RdbReviewService implements ReviewService {

    private final ReviewRepository reviewRepository;

    public RdbReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Iterable<Review> findAllByBook(Integer bookId) {
        return null;
    }

    @Override
    public Optional<Review> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Review save(Review review) {
        review.setWrittenIn(new Date());  // установить дату формирования
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> update(Review review) {
        return Optional.empty();
    }

    @Override
    public Optional<Review> deleteById(Integer id) {
        return Optional.empty();
    }
}
