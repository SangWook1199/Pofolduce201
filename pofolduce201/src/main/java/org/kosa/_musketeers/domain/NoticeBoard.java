package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class NoticeBoard {
	private int noticeId;
    private String title;
    private String contentHtml;
    private int noticeCount;
    private LocalDateTime date;
    private User userId;
	
	public NoticeBoard() {
		super();
	}
	
	public NoticeBoard(int noticeId, String title, String contentHtml, int noticeCount, LocalDateTime date, User userId) {
		super();
		this.noticeId = noticeId;
		this.title = title;
		this.contentHtml = contentHtml;
		this.noticeCount = noticeCount;
		this.date = date;
		this.userId = userId;
	}
	
	public NoticeBoard(String title, String contentHtml, int noticeCount, LocalDateTime date, User userId) {
		super();
		this.title = title;
		this.contentHtml = contentHtml;
		this.noticeCount = noticeCount;
		this.date = date;
		this.userId = userId;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public int getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "NoticeBoard [noticeId=" + noticeId + ", title=" + title + ", contentHtml=" + contentHtml
				+ ", noticeCount=" + noticeCount + ", date=" + date + ", userId=" + userId + "]";
	}
	
}
