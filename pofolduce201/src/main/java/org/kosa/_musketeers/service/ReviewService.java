package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.mapper.ReviewMapper;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	private final UserMapper userMapper;
	public ReviewService(ReviewMapper reviewMapper, UserMapper userMapper) {
		this.reviewMapper = reviewMapper;
		this.userMapper = userMapper;
	}

	public void addReview(Review review) {
		reviewMapper.insertReview(review);
		userMapper.updateUserPoint(review.getUser().getUserId(), 5);
	}

	public List<Review> findReviewsByReviewPostId(int reviewPostId) {
		return reviewMapper.selectReviewsByReviewPostId(reviewPostId);
	}

	public void removeReview(int reviewId) {
		reviewMapper.deleteReview(reviewId);
	}

	public void modifyReview(int reviewId, String reviewContents) {
		reviewMapper.updateReview(reviewId, reviewContents);
	}

	public void plusReviewLike(int reviewId, int reviewUserId) {
		reviewMapper.updateReviewLike(reviewId);
		userMapper.updateUserPoint(reviewUserId, 15);
	}

}
