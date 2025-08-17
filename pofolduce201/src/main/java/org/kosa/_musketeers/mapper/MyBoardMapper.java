package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.domain.ReviewBoard;
import org.kosa._musketeers.domain.ReviewPostComment;
import org.kosa._musketeers.domain.StudyBoard;
import org.kosa._musketeers.domain.StudyPostComment;

@Mapper
public interface MyBoardMapper {

	ReviewBoard[] findMyReviewPost(int userId);

	StudyBoard[] findMyStudyPost(int userId);

	ReviewPostComment[] findMyReviewComment(int userId);

	StudyPostComment[] findMyStudyComment(int userId);

	List<Review> findMyReview(int userId);

	int countMyReview(int userId);

}
