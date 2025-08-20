package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.ReviewPost;
import org.kosa._musketeers.domain.ReviewPostComment;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.ReviewBoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewBoardService {

	private ReviewBoardMapper reviewBoardMapper;
	public ReviewBoardService(ReviewBoardMapper reviewBoardMapper) {
		this.reviewBoardMapper = reviewBoardMapper;
	}
	
	public List<ReviewPost> getReviewPostList(int page) {
		return reviewBoardMapper.getReviewPostList((page - 1) * 15, 15);
	}
	
	public int getTotalReviewPostCount() {
		return reviewBoardMapper.getTotalReviewPostCount();
	}

	public List<ReviewPost> getBestReviewPostList() {
		return reviewBoardMapper.getBestReviewPostList();
	}

	public void createPost(ReviewPost reviewPost) {
		reviewBoardMapper.createPost(reviewPost);
	}

	@Transactional
	public ReviewPost viewPost(int reviewPostId) {
		reviewBoardMapper.updateReviewPostViewCount(reviewPostId);
		return reviewBoardMapper.getReviewPostByReviewPostId(reviewPostId);
	}

	public void deleteReviewPost(int reviewPostId) {
		reviewBoardMapper.delteReviewPost(reviewPostId);
	}

	public ReviewPost getReviewPostById(int reviewPostId) {
		return reviewBoardMapper.getReviewPostByReviewPostId(reviewPostId);
	}

	public void editReviewPost(ReviewPost reviewPost) {
		reviewBoardMapper.updateReviewPostByReviewId(reviewPost);
	}

	public void writeComment(int userId, int reviewPostId, String comment) {
		User user = new User(userId);
		ReviewPostComment reviewPostComment = new ReviewPostComment(comment, user, reviewPostId);
		reviewBoardMapper.createReviewPostComment(reviewPostComment);
	}

	public List<ReviewPostComment> loadReviewPostCommentList(int reviewPostId) {
		return reviewBoardMapper.getReviewCommentListByReviewPostId(reviewPostId);
	}

	public void deleteComment(int reviewCommentId) {
		reviewBoardMapper.deleteReviewComment(reviewCommentId);
	}
	
}
