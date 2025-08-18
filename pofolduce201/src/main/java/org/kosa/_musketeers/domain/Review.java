package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

//첨삭 VO 입니다.
public class Review {

	private int reviewId;
	private int reviewLocationX;
	private int reviewLocationY;
	private String reviewContents;
	private LocalDateTime reviewDate;
	private int likeCount;
	private int reviewPostId;
	private int userId;
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Review(int reviewId, int reviewLocationX, int reviewLocationY, String reviewContents, LocalDateTime reviewDate,
			int likeCount, int reviewPostId, int userId) {
		super();
		this.reviewId = reviewId;
		this.reviewLocationX = reviewLocationX;
		this.reviewLocationY = reviewLocationY;
		this.reviewContents = reviewContents;
		this.reviewDate = reviewDate;
		this.likeCount = likeCount;
		this.reviewPostId = reviewPostId;
		this.userId = userId;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getReviewLocationX() {
		return reviewLocationX;
	}
	public void setReviewLocationX(int reviewLocationX) {
		this.reviewLocationX = reviewLocationX;
	}
	public int getReviewLocationY() {
		return reviewLocationY;
	}
	public void setReviewLocationY(int reviewLocationY) {
		this.reviewLocationY = reviewLocationY;
	}
	public String getReviewContents() {
		return reviewContents;
	}
	public void setReviewContents(String reviewContents) {
		this.reviewContents = reviewContents;
	}
	public LocalDateTime getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(LocalDateTime reviewDate) {
		this.reviewDate = reviewDate;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getReviewPostId() {
		return reviewPostId;
	}
	public void setReviewPostId(int reviewPostId) {
		this.reviewPostId = reviewPostId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", reviewLocationX=" + reviewLocationX + ", reviewLocationY="
				+ reviewLocationY + ", reviewContents=" + reviewContents + ", reviewDate=" + reviewDate + ", likeCount="
				+ likeCount + ", reviewPostId=" + reviewPostId + ", userId=" + userId + "]";
	}
	
	

}
