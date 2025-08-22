package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

//첨삭 게시물 VO 입니다.
public class ReviewBoard {
	private int reviewPostId;
	private String title;
	private int viewCount;
	private String postHtml;
	private String portfolio_html;
	private LocalDateTime createDate;
	private int likeCount;
	private int userId;
	
	public ReviewBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewBoard(int reviewPostId, String title, int viewCount, String postHtml, String portfolio_html,
			LocalDateTime createDate, int likeCount, int userId) {
		super();
		this.reviewPostId = reviewPostId;
		this.title = title;
		this.viewCount = viewCount;
		this.postHtml = postHtml;
		this.portfolio_html = portfolio_html;
		this.createDate = createDate;
		this.likeCount = likeCount;
		this.userId = userId;
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

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getPostHtml() {
		return postHtml;
	}

	public void setPostHtml(String postHtml) {
		this.postHtml = postHtml;
	}

	public String getPortfolio_html() {
		return portfolio_html;
	}

	public void setPortfolio_html(String portfolio_html) {
		this.portfolio_html = portfolio_html;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ReviewBoard [reviewPostId=" + reviewPostId + ", title=" + title + ", viewCount=" + viewCount
				+ ", postHtml=" + postHtml + ", portfolio_html=" + portfolio_html + ", createDate=" + createDate
				+ ", likeCount=" + likeCount + ", userId=" + userId + "]";
	}

	
	
}
