package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class StudyBoardComment {
	private int commentsId;
	private String commentsContents;
	private LocalDateTime commentsDate;
	private StudyBoard studyId;
	private User userId;

	public StudyBoardComment() {
		super();

	}

	public StudyBoardComment(int commentsId, String commentsContents, LocalDateTime commentsDate, StudyBoard studyId,
			User userId) {
		super();
		this.commentsId = commentsId;
		this.commentsContents = commentsContents;
		this.commentsDate = commentsDate;
		this.studyId = studyId;
		this.userId = userId;
	}

	public StudyBoardComment(String commentsContents, StudyBoard studyId, User userId) {
		super();
		this.commentsContents = commentsContents;
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

	public StudyBoard getStudyId() {
		return studyId;
	}

	public void setStudyId(StudyBoard studyId) {
		this.studyId = studyId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "StudyBoardComment [commentsId=" + commentsId + ", commentsContents=" + commentsContents
				+ ", commentsDate=" + commentsDate + ", studyId=" + studyId + ", userId=" + userId + "]";
	}

}
