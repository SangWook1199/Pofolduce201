package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.MyPageReviewComment;
import org.kosa._musketeers.domain.MyPageStudyComment;
import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.domain.ReviewBoard;
import org.kosa._musketeers.domain.StudyBoard;

@Mapper
public interface MyBoardMapper {

	ReviewBoard[] findMyReviewPost(int userId);

	StudyBoard[] findMyStudyPost(int userId);

	MyPageReviewComment[] findMyReviewComment(int userId);

	MyPageStudyComment[] findMyStudyComment(int userId);

	List<Review> findMyReview(int userId);

	int countMyReview(int userId);

}
