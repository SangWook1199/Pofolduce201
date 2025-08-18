package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

/**
 * 내 댓글을 총합하기 위한 vo 입니다.
 */
public class MyPageComment {
	private int commentsId;
    private String boardType;       // 게시판 구분 ("review", "study")

	private String commentsContents;
	private LocalDateTime commentsDate;
	private int userId;
	
	//선택 필드
	private Integer reviewId;
	private Integer studyId;
	
	 public MyPageComment() {}

	 public MyPageComment(int commentsId,String boardType, String commentsContents, LocalDateTime commentsDate, int userId,
			 Integer reviewId, Integer studyId) {
		super();
		this.commentsId = commentsId;
		this.commentsContents = commentsContents;
		this.commentsDate = commentsDate;
		this.userId = userId;
		this.boardType = boardType;
		this.reviewId = reviewId;
		this.studyId = studyId;
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

	 public int getUserId() {
		 return userId;
	 }

	 public void setUserId(int userId) {
		 this.userId = userId;
	 }

	 public String getBoardType() {
		 return boardType;
	 }

	 public void setBoardType(String boardType) {
		 this.boardType = boardType;
	 }

	 public int getReviewId() {
		 return reviewId;
	 }

	 public void setReviewId(Integer reviewId) {
		 this.reviewId = reviewId;
	 }

	 public int getStudyId() {
		 return studyId;
	 }

	 public void setStudyId(Integer studyId) {
		 this.studyId = studyId;
	 }

	 @Override
	 public String toString() {
		return "MyPageComment [commentsId=" + commentsId + ", commentsContents=" + commentsContents + ", commentsDate="
				+ commentsDate + ", userId=" + userId + ", boardType=" + boardType + ", reviewId=" + reviewId
				+ ", studyId=" + studyId + "]";
	 }
	 
	 

	
}
