package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

/**
 * 내 게시글 조회를 통합하기 위한 vo 입니다.
 */

public class MyPagePost {
	private int postId;             // 게시글 id
    private String boardType;       // 게시판 구분 ("review", "study")
    private String title;
    private int viewCount;
    private String postHtml;
    private LocalDateTime createDate;
    private int likeCount;
    private int userId;
    
 // 선택적으로 필요한 필드
    private Integer portfolioId;    // ReviewBoard 전용
    private String address;         // StudyBoard 전용

    public MyPagePost() {}

	public MyPagePost(int postId, String boardType, String title, int viewCount, String postHtml,
			LocalDateTime createDate, int likeCount, int userId, Integer portfolioId, String address) {
		super();
		this.postId = postId;
		this.boardType = boardType;
		this.title = title;
		this.viewCount = viewCount;
		this.postHtml = postHtml;
		this.createDate = createDate;
		this.likeCount = likeCount;
		this.userId = userId;
		this.portfolioId = portfolioId;
		this.address = address;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
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

	public Integer getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "MyPagePostVO [postId=" + postId + ", boardType=" + boardType + ", title=" + title + ", viewCount="
				+ viewCount + ", postHtml=" + postHtml + ", createDate=" + createDate + ", likeCount=" + likeCount
				+ ", userId=" + userId + ", portfolioId=" + portfolioId + ", address=" + address + "]";
	}
    
    
}
