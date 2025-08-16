package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class StudyBoard {
	private int studyId;
	private String title;
	private int viewCount;
	private String postHtml;
	private LocalDateTime postDate;
	private int likeCount;
	private String address;
	private User userId;

	public StudyBoard() {
		super();

	}

	public StudyBoard(int studyId, String title, int viewCount, String postHtml, LocalDateTime postDate, int likeCount,
			String address, User userId) {
		super();
		this.studyId = studyId;
		this.title = title;
		this.viewCount = viewCount;
		this.postHtml = postHtml;
		this.postDate = postDate;
		this.likeCount = likeCount;
		this.address = address;
		this.userId = userId;
	}

	public StudyBoard(String title, int viewCount, String postHtml, int likeCount, String address, User userId) {
		super();
		this.title = title;
		this.viewCount = viewCount;
		this.postHtml = postHtml;
		this.likeCount = likeCount;
		this.address = address;
		this.userId = userId;
	}

	public int getStudyId() {
		return studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
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

	public LocalDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "StudyBoard [studyId=" + studyId + ", title=" + title + ", viewCount=" + viewCount + ", postHtml="
				+ postHtml + ", postDate=" + postDate + ", likeCount=" + likeCount + ", address=" + address
				+ ", userId=" + userId + "]";
	}

}
