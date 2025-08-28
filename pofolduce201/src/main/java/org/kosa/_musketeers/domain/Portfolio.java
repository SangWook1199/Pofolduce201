package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class Portfolio {
	private int portfolioId;
	private String portfolioName;
	private User userId;
	private String fileLocation;
	private LocalDateTime uploadDate;

	public Portfolio() {
		super();

	}

	public Portfolio(int portfolioId, String portfolioName, User userId, LocalDateTime uploadDate) {
		super();
		this.portfolioId = portfolioId;
		this.portfolioName = portfolioName;
		this.userId = userId;
		this.uploadDate = uploadDate;
	}

	public Portfolio(String portfolioName, User userId) {
		super();
		this.portfolioName = portfolioName;
		this.userId = userId;
	}

	
	public Portfolio(String portfolioName, User userId, String fileLocation) {
		super();
		this.portfolioName = portfolioName;
		this.userId = userId;
		this.fileLocation = fileLocation;
	}

	public int getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Override
	public String toString() {
		return "Portfolio [portfolioId=" + portfolioId + ", portfolioName=" + portfolioName + ", userId=" + userId
				+ ", fileLocation=" + fileLocation + ", uploadDate=" + uploadDate + "]";
	}
	
	
}
