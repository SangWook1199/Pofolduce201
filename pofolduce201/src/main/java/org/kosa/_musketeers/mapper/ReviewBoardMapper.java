package org.kosa._musketeers.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.ReviewPost;
import org.kosa._musketeers.domain.ReviewPostComment;

@Mapper
public interface ReviewBoardMapper {

	ReviewPost getReviewPostByReviewPostId(int reviewPostId);

	List<ReviewPost> getReviewPostList(int start, int count);
	
	int getTotalReviewPostCount();

	List<ReviewPost> getBestReviewPostList();

	void createPost(ReviewPost reviewPost);

	void updateReviewPostViewCount(int reviewPostId);

	void delteReviewPost(int reviewPostId);

	void updateReviewPostByReviewId(ReviewPost reviewPost);
	
	void createReviewPostComment(ReviewPostComment reviewPostComment);

	List<ReviewPostComment> getReviewCommentListByReviewPostId(int reviewPostId);

	void deleteReviewComment(int reviewCommentId);
	
	int getTotalReviewPostCountById(int userId);

	List<Map<String, Object>> getReviewBoardByViewCount();
}
