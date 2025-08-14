package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class User {
	private int userId;
	private String email;
	private String nickname;
	private String password;
	private String userType;
	private String companyCertification;
	private String companyName;
	private int point;
	private int sanctionCount;
	private String sanctionType;
	private int sanctionPeriod;
	private LocalDateTime date;
	private String userImageLocation;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String email, String nickname, String password, String userType,
			String companyCertification, String companyName, int point, int sanctionCount, String sanctionType,
			int sanctionPeriod, LocalDateTime date, String userImageLocation) {
		super();
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.userType = userType;
		this.companyCertification = companyCertification;
		this.companyName = companyName;
		this.point = point;
		this.sanctionCount = sanctionCount;
		this.sanctionType = sanctionType;
		this.sanctionPeriod = sanctionPeriod;
		this.date = date;
		this.userImageLocation = userImageLocation;
	}

	public User(String email, String nickname, String password, String userType, String companyCertification,
			String companyName, int point, int sanctionCount, String sanctionType, int sanctionPeriod,
			LocalDateTime date, String userImageLocation) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.userType = userType;
		this.companyCertification = companyCertification;
		this.companyName = companyName;
		this.point = point;
		this.sanctionCount = sanctionCount;
		this.sanctionType = sanctionType;
		this.sanctionPeriod = sanctionPeriod;
		this.date = date;
		this.userImageLocation = userImageLocation;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCompanyCertification() {
		return companyCertification;
	}

	public void setCompanyCertification(String companyCertification) {
		this.companyCertification = companyCertification;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getSanctionCount() {
		return sanctionCount;
	}

	public void setSanctionCount(int sanctionCount) {
		this.sanctionCount = sanctionCount;
	}

	public String getSanctionType() {
		return sanctionType;
	}

	public void setSanctionType(String sanctionType) {
		this.sanctionType = sanctionType;
	}

	public int getSanctionPeriod() {
		return sanctionPeriod;
	}

	public void setSanctionPeriod(int sanctionPeriod) {
		this.sanctionPeriod = sanctionPeriod;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getUserImageLocation() {
		return userImageLocation;
	}

	public void setUserImageLocation(String userImageLocation) {
		this.userImageLocation = userImageLocation;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", nickname=" + nickname + ", password=" + password
				+ ", userType=" + userType + ", companyCertification=" + companyCertification + ", companyName="
				+ companyName + ", point=" + point + ", sanctionCount=" + sanctionCount + ", sanctionType="
				+ sanctionType + ", sanctionPeriod=" + sanctionPeriod + ", date=" + date + ", userImageLocation="
				+ userImageLocation + "]";
	}

}
