package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class MyPageStudyPost {
	private int studyId;
	private String title;
	private int viewCount;
	private String postHtml;
	private LocalDateTime postDate;
	private int likeCount;
	private String address;
	private int userId;
	public MyPageStudyPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyPageStudyPost(int studyId, String title, int viewCount, String postHtml, LocalDateTime postDate,
			int likeCount, String address, int userId) {
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "MyPageStudyPost [studyId=" + studyId + ", title=" + title + ", viewCount=" + viewCount + ", postHtml="
				+ postHtml + ", postDate=" + postDate + ", likeCount=" + likeCount + ", address=" + address
				+ ", userId=" + userId + "]";
	}
	
	
}
