package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.mapper.ReviewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	public ReviewService(ReviewMapper reviewMapper) {
		this.reviewMapper = reviewMapper;
	}

	public void addReview(Review review) {
		reviewMapper.insertReview(review);
	}

	public List<Review> findReviewsByReviewPostId(int reviewPostId) {
		return reviewMapper.selectReviewsByReviewPostId(reviewPostId);
	}

	public void removeReview(int reviewId) {
		reviewMapper.deleteReview(reviewId);
	}

}
