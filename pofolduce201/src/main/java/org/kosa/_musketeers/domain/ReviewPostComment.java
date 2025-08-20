package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

//첨삭 게시물 댓글 VO 입니다.
public class ReviewPostComment {
	private int commentsId;
	private String commentsContents;
	private LocalDateTime commentsDate;
	private int reviewPostId;
	private User userId;
	public ReviewPostComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReviewPostComment(int commentsId, String commentsContents, LocalDateTime commentsDate, int reviewPostId,
			User userId) {
		super();
		this.commentsId = commentsId;
		this.commentsContents = commentsContents;
		this.commentsDate = commentsDate;
		this.reviewPostId = reviewPostId;
		this.userId = userId;
	}
	public ReviewPostComment(String commentsContents, User userId, int reviewPostId) {
		this.commentsContents = commentsContents;
		this.userId = userId;
		this.reviewPostId = reviewPostId;
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
	public int getReviewPostId() {
		return reviewPostId;
	}
	public void setReviewPostId(int reviewPostId) {
		this.reviewPostId = reviewPostId;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ReviewPostComment [commentsId=" + commentsId + ", commentsContents=" + commentsContents
				+ ", commentsDate=" + commentsDate + ", reviewPostId=" + reviewPostId + ", userId=" + userId + "]";
	}
	
	
}
