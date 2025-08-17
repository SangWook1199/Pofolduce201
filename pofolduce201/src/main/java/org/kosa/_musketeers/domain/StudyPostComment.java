package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

//스터디 게시글의 댓글 VO 입니다.
public class StudyPostComment {

	private int commentsId;
	private String commentsContents;
	private LocalDateTime commentsDate;
	private int studyId;
	private int userId;
	public StudyPostComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudyPostComment(int commentsId, String commentsContents, LocalDateTime commentsDate, int studyId,
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
	public void setCommentId(int commentsId) {
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
		return "StudyPostComment [commentsId=" + commentsId + ", commentsContents=" + commentsContents + ", commentsDate="
				+ commentsDate + ", studyId=" + studyId + ", userId=" + userId + "]";
	}
	
	
	
}
