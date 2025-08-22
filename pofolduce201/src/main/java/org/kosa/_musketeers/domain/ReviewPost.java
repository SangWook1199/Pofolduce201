package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class ReviewPost {
	private int reviewPostId;
	private String title;
	private String postHtml;
	private String portfolioHtml;
	private int viewCount;
	private LocalDateTime createDate;
	private int likeCount;
	private User user;

	// 기본생성자
	public ReviewPost() {
		super();
	}

	// 전체 생성자
	public ReviewPost(int reviewPostId, String title, String postHtml, String portfolioHtml, int viewCount,
			LocalDateTime createDate, int likeCount, User user) {
		super();
		this.reviewPostId = reviewPostId;
		this.title = title;
		this.postHtml = postHtml;
		this.portfolioHtml = portfolioHtml;
		this.viewCount = viewCount;
		this.createDate = createDate;
		this.likeCount = likeCount;
		this.user = user;
	}

	public ReviewPost(int reviewPostId, String title, String postHtml, int viewCount, LocalDateTime createDate,
			int likeCount, int userId, String nickname) {
		super();
		this.reviewPostId = reviewPostId;
		this.title = title;
		this.postHtml = postHtml;
		this.viewCount = viewCount;
		this.createDate = createDate;
		this.likeCount = likeCount;
		this.user = new User(userId, nickname);
	}

	public ReviewPost(String title, String postHtml, String portfolioHtml, User user) {
		super();
		this.title = title;
		this.postHtml = postHtml;
		this.user = user;
	}
	
	public ReviewPost(String title, String postHtml, String portfolioHtml) {
		super();
		this.title = title;
		this.postHtml = postHtml;
	}

	public ReviewPost(int reviewPostId, String title, int viewCount, LocalDateTime createDate, int userId,
			String nickname) {
		super();
		this.reviewPostId = reviewPostId;
		this.title = title;
		this.viewCount = viewCount;
		this.createDate = createDate;
		this.user = new User(userId, nickname);
	}

	public int getReviewPostId() {
		return reviewPostId;
	}

	public void setReviewPostId(int reviewPostId) {
		this.reviewPostId = reviewPostId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostHtml() {
		return postHtml;
	}

	public void setPostHtml(String postHtml) {
		this.postHtml = postHtml;
	}

	public String getPortfolioHtml() {
		return portfolioHtml;
	}

	public void setPortfolioHtml(String portfolioHtml) {
		this.portfolioHtml = portfolioHtml;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ReviewPost [reviewPostId=" + reviewPostId + ", title=" + title + ", postHtml=" + postHtml
				+ ", viewCount=" + viewCount + ", createDate=" + createDate + ", likeCount=" + likeCount + ", user="
				+ user + "]";
	}

}
