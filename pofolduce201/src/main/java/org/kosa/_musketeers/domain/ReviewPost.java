package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class ReviewPost {
	private int reviewPostId;
	private String title;
	private String postHtml;
	private int viewCount;
	private LocalDateTime createDate;
	private int likeCount;
	private User user;
	private Portfolio portfolio;

	public ReviewPost() {
		super();
	}

	public ReviewPost(int reviewPostId, String title, String postHtml, int viewCount, LocalDateTime createDate,
			int likeCount, User user, Portfolio portfolio) {
		super();
		this.reviewPostId = reviewPostId;
		this.title = title;
		this.postHtml = postHtml;
		this.viewCount = viewCount;
		this.createDate = createDate;
		this.likeCount = likeCount;
		this.user = user;
		this.portfolio = portfolio;
	}
	
	public ReviewPost(int reviewPostId, String title, String postHtml, int viewCount, LocalDateTime createDate,
			int likeCount, int userId, Portfolio portfolio, String nickname) {
		super();
		this.reviewPostId = reviewPostId;
		this.title = title;
		this.postHtml = postHtml;
		this.viewCount = viewCount;
		this.createDate = createDate;
		this.likeCount = likeCount;
		this.user = new User(userId, nickname);
		this.portfolio = portfolio;
	}

	public ReviewPost(String title, String postHtml, User user, Portfolio portfolio) {
		super();
		this.title = title;
		this.postHtml = postHtml;
		this.user = user;
		this.portfolio = portfolio;
	}
	
	public ReviewPost(int reviewPostId, String title, int viewCount, LocalDateTime createDate, int userId, String nickname) {
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

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "ReviewPost [reviewPostId=" + reviewPostId + ", title=" + title + ", postHtml=" + postHtml
				+ ", viewCount=" + viewCount + ", createDate=" + createDate + ", likeCount=" + likeCount + ", user="
				+ user + ", portfolio=" + portfolio + "]";
	}

	
}
