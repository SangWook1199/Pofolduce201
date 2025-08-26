package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

//회사 인증 정보 VO 입니다.
public class Verification {
	
	private int verificationId;
	private String companyName;
	private String state;
	private LocalDateTime date;
	private String fileLocation;
	private User user;
	
	public Verification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Verification(int verificationId, String companyName, String state, LocalDateTime date, String fileLocation,
			User user) {
		super();
		this.verificationId = verificationId;
		this.companyName = companyName;
		this.state = state;
		this.date = date;
		this.fileLocation = fileLocation;
		this.user = user;
	}

	public int getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(int verificationId) {
		this.verificationId = verificationId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Verification [verificationId=" + verificationId + ", companyName=" + companyName + ", state=" + state
				+ ", date=" + date + ", fileLocation=" + fileLocation + ", user=" + user + "]";
	}
	
	
	

}
