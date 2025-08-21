package org.kosa._musketeers.domain;

import java.time.LocalDateTime;
//마이페이지 전용 vo 입니다.
public class MyPageStudyComment {
	private int commentsId;
	private String commentsContents;
	private LocalDateTime commentsDate;
	private int studyId;
	private int userId;
	public MyPageStudyComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyPageStudyComment(int commentsId, String commentsContents, LocalDateTime commentsDate, int studyId,
			int userId) {
		super();
		this.commentsId = commentsId;
		this.commentsContents = commentsContents;
		this.commentsDate = commentsDate;
		this.studyId = studyId;
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
	public int getStudyId() {
		return studyId;
	}
	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "MyPageStudyComment [commentsId=" + commentsId + ", commentsContents=" + commentsContents
				+ ", commentsDate=" + commentsDate + ", studyId=" + studyId + ", userId=" + userId + "]";
	}

}
