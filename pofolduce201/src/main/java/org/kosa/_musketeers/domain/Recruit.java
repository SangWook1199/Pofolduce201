package org.kosa._musketeers.domain;

public class Recruit {
	private String recruitId;
	private String company;
	private String content;
	private String job;
	private String link;
	private String imgLink;
	public Recruit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Recruit(String recruitId, String company, String content, String job, String link, String imgLink) {
		super();
		this.recruitId = recruitId;
		this.company = company;
		this.content = content;
		this.job = job;
		this.link = link;
		this.imgLink = imgLink;
	}
	public String getRecruitId() {
		return recruitId;
	}
	public void setRecruitId(String recruitId) {
		this.recruitId = recruitId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImgLink() {
		return imgLink;
	}
	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
	@Override
	public String toString() {
		return "Recruit [recruitId=" + recruitId + ", company=" + company + ", content=" + content + ", job=" + job
				+ ", link=" + link + ", imgLink=" + imgLink + "]";
	}
	
	
}
