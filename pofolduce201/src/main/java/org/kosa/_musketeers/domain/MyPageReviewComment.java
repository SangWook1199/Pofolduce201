package org.kosa._musketeers.domain;

import java.time.LocalDateTime;
//마이페이지 전용 vo 입니다.

public class MyPageReviewComment {
	
	private int commentsId;
	private String commentsContents;
	private LocalDateTime commentsDate;
	private int reviewId;
	private int userId;
	public MyPageReviewComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyPageReviewComment(int commentsId, String commentsContents, LocalDateTime commentsDate, int reviewId,
			int userId) {
		super();
		this.commentsId = commentsId;
		this.commentsContents = commentsContents;
		this.commentsDate = commentsDate;
		this.reviewId = reviewId;
		this.userId = userId;
	}
	public int getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}
	public String getCommentsContents() {
		return commentsContents;
	}
	public void setCommentsContents(String commentsContents) {
		this.commentsContents = commentsContents;
	}
	public LocalDateTime getCommentsDate() {
		return commentsDate;
	}
	public void setCommentsDate(LocalDateTime commentsDate) {
		this.commentsDate = commentsDate;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "MyPageReviewComment [commentsId=" + commentsId + ", commentsContents=" + commentsContents
				+ ", commentsDate=" + commentsDate + ", reviewId=" + reviewId + ", userId=" + userId + "]";
	}

}
