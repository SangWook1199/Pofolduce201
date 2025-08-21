package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class Report {
	private int reportId;
	private String location;
	private String reportContents;
	private String state;
	private LocalDateTime date;
	private User userId;
	private User reportedId;

	public Report() {
		super();

	}

	public Report(int reportId, String location, String reportContents, String state, LocalDateTime date, User userId,
			User reportedId) {
		super();
		this.reportId = reportId;
		this.location = location;
		this.reportContents = reportContents;
		this.state = state;
		this.date = date;
		this.userId = userId;
		this.reportedId = reportedId;
	}

	public Report(String location, String reportContents, User userId, User reportedId) {
		super();
		this.location = location;
		this.reportContents = reportContents;
		this.userId = userId;
		this.reportedId = reportedId;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReportContents() {
		return reportContents;
	}

	public void setReportContents(String reportContents) {
		this.reportContents = reportContents;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public User getReportedId() {
		return reportedId;
	}

	public void setReportedId(User reportedId) {
		this.reportedId = reportedId;
	}

	@Override
	public String toString() {
		return "Report [reportId=" + reportId + ", location=" + location + ", reportContents=" + reportContents
				+ ", state=" + state + ", date=" + date + ", userId=" + userId + ", reportedId=" + reportedId + "]";
	}

}
