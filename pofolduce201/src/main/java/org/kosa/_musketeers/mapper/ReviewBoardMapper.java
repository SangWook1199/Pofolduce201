package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.ReviewPost;

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
}
