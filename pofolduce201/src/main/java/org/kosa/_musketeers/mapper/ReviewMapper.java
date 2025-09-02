package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.Review;

@Mapper
public interface ReviewMapper {

	// 리뷰를 추가하는 메서드, 리뷰 객체를 매개변수로 받습니다
	public void insertReview(Review review);

	// 리뷰 리스트를 반환하는 메서드, 리뷰 게시물의 id를 매개변수로 받습니다
	public List<Review> selectReviewsByReviewPostId(int reviewPostId);

	// 리뷰를 삭제하는 메서드, 리뷰 게시물의 id를 매개변수로 받습니다.
	public void deleteReview(int reviewId);

	// 리뷰를 업데이트 하는 메서드, 리뷰 게시물 id와 내용 문자열을 매개변수로 받습니다
	public void updateReview(int reviewId, String reviewContents);

	// 리뷰의 좋아요를 업데이트 하는 메서드, reviewId를 매개변수로 받습니다
	public void updateReviewLike(int reviewId);
}
