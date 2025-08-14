package org.kosa._musketeers.domain;

import java.time.LocalDateTime;

public class User {
	private Integer user_id;
	private String email;
	private String nickname;
	private String password;
	private String user_type;
	private String company_certification;
	private String company_name;
	private Integer point;
	private Integer sanction_count;
	private String sanction_type;
	private Integer sanction_period;
	private LocalDateTime date;
	private String user_image_location;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public User(Integer user_id, String email, String nickname, String password, String user_type,
			String company_certification, String company_name, Integer point, Integer sanction_count,
			String sanction_type, Integer sanction_period, LocalDateTime date, String user_image_location) {
		super();
		this.user_id = user_id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.user_type = user_type;
		this.company_certification = company_certification;
		this.company_name = company_name;
		this.point = point;
		this.sanction_count = sanction_count;
		this.sanction_type = sanction_type;
		this.sanction_period = sanction_period;
		this.date = date;
		this.user_image_location = user_image_location;
	}



	public Integer getUser_id() {
		return user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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


	public String getUser_type() {
		return user_type;
	}


	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}


	public String getCompany_certification() {
		return company_certification;
	}


	public void setCompany_certification(String company_certification) {
		this.company_certification = company_certification;
	}


	public String getCompany_name() {
		return company_name;
	}


	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}


	public Integer getPoint() {
		return point;
	}


	public void setPoint(Integer point) {
		this.point = point;
	}


	public Integer getSanction_count() {
		return sanction_count;
	}


	public void setSanction_count(Integer sanction_count) {
		this.sanction_count = sanction_count;
	}


	public String getSanction_type() {
		return sanction_type;
	}


	public void setSanction_type(String sanction_type) {
		this.sanction_type = sanction_type;
	}


	public Integer getSanction_period() {
		return sanction_period;
	}


	public void setSanction_period(Integer sanction_period) {
		this.sanction_period = sanction_period;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public String getUser_image_location() {
		return user_image_location;
	}


	public void setUser_image_location(String user_image_location) {
		this.user_image_location = user_image_location;
	}



	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", email=" + email + ", nickname=" + nickname + ", password=" + password
				+ ", user_type=" + user_type + ", company_certification=" + company_certification + ", company_name="
				+ company_name + ", point=" + point + ", sanction_count=" + sanction_count + ", sanction_type="
				+ sanction_type + ", sanction_period=" + sanction_period + ", date=" + date + ", user_image_location="
				+ user_image_location + "]";
	}
	
}
