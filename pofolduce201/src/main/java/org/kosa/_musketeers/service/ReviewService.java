package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.domain.User;
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

	// 첨삭을 생성합니다. User를 생성하고, userId값을 설정한 뒤, Review에 User의 값을 넣어줍니다.
	// 이후 reviewMapper에서 리뷰를 insert 합니다.
	// 리뷰를 생성하면 유저의 point도 함께 올라가므로, userMapper를 사용하여 user의 점수를 올려줍니다.
	public void addReview(Review review, Integer userId) {
		review.setUser(new User(userId));
		reviewMapper.insertReview(review);
		userMapper.updateUserPoint(review.getUser().getUserId(), 5*2);
	}

	// 리뷰의 리스트를 반환합니다. 
	// reviewMapper에게 reviewPostId를 전달하여, 해당 reviewPostId에 해당하는 review의 리스트를 반환합니다.
	public List<Review> findReviewsByReviewPostId(int reviewPostId) {
		return reviewMapper.selectReviewsByReviewPostId(reviewPostId);
	}

	// 리뷰를 삭제합니다.
	// reviewMapper에게 reviewId를 전달하여 리뷰를 삭제합니다.
	public void removeReview(int reviewId) {
		reviewMapper.deleteReview(reviewId);
	}

	// 리뷰의 내용을 수정합니다.
	// reviewMapper에게 reviewId, reviewContent를 전달하여 리뷰의 내용을 수정합니다.
	public void modifyReview(int reviewId, String reviewContents) {
		reviewMapper.updateReview(reviewId, reviewContents);
	}

	// 리뷰에 좋아요를 처리합니다.
	// 리뷰에 좋아요를 받으면 review를 작성한 유저의 점수가 올라가므로 userMapper에게 reviewUserId를 전달하여 점수를 올려줍니다.
	public void plusReviewLike(int reviewId, int reviewUserId) {
		reviewMapper.updateReviewLike(reviewId);
		userMapper.updateUserPoint(reviewUserId, 15);
	}

}
