package org.kosa._musketeers.controller;

import org.kosa._musketeers.domain.Report;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class ReportController {
	private final ReportService reportService;

	public ReportController(ReportService reportService) {
		super();
		this.reportService = reportService;
	}

	@PostMapping("/study/{studyId}/report")
	public String reportStudyBoard(@RequestParam String location, @RequestParam("reportContents") String contents,
			@RequestParam int reportedId, @PathVariable int studyId, @SessionAttribute(name="userId",required=false) int userId) {
		Report report = new Report();

		User user = new User();
		user.setUserId(userId);
		User reportedUser = new User();
		report.setUserId(user);

		report.setLocation(location);
		report.setReportContents(contents);
		report.setState("처리 전");
		reportedUser.setUserId(reportedId);
		report.setReportedId(reportedUser);
		reportService.createReport(report);

		return "redirect:/study/" + studyId;
	}
	
	@PostMapping("/review/post{reviewPostId}/report")
	public String reportReviewBoard(@RequestParam String location, @RequestParam("reportContents") String contents,
			@RequestParam int reportedId, @PathVariable int reviewPostId, @SessionAttribute(name="userId",required=false) int userId) {
		Report report = new Report();

		User user = new User();
		user.setUserId(userId);
		User reportedUser = new User();
		report.setUserId(user);

		report.setLocation(location);
		report.setReportContents(contents);
		report.setState("처리 전");
		reportedUser.setUserId(reportedId);
		report.setReportedId(reportedUser);
		reportService.createReport(report);

		return "redirect:/review/post?reviewPostId=" + reviewPostId;
	}
}
