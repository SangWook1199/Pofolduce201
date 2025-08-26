package org.kosa._musketeers.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kosa._musketeers.domain.*;
import org.kosa._musketeers.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

	private final UserService userService;
	private final AdminService adminService;
	private final ReportService reportService;
	private final StudyBoardService studyBoardService;
	private final ReviewBoardService reviewPostService;
	private final StudyBoardCommentService studyBoardCommentService;

	public AdminUserController(UserService userService, AdminService adminService, ReportService reportService,
			StudyBoardService studyBoardService, ReviewBoardService reviewPostService,
			StudyBoardCommentService studyBoardCommentService) {
		this.userService = userService;
		this.adminService = adminService;
		this.reportService = reportService;
		this.studyBoardService = studyBoardService;
		this.reviewPostService = reviewPostService;
		this.studyBoardCommentService = studyBoardCommentService;
	}

	@GetMapping
	public String adminPage(Model model, HttpServletRequest request) {
		List<User> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);
		model.addAttribute("currentPath", request.getRequestURI());
		return "pages/admin/admin-home";
	}

	@GetMapping("/user/{userId}")
	public String userDetail(@PathVariable int userId, Model model, HttpServletRequest request) {
		User user = userService.getUserById(userId);
		model.addAttribute("userData", user);
		model.addAttribute("currentPath", request.getRequestURI());
		return "pages/admin/admin-userpage";
	}

	@PostMapping("/user/{userId}/sanction")
	@ResponseBody
	public ResponseEntity<String> sanctionUser(@PathVariable int userId, @RequestParam("days") int days) {
		try {
			adminService.sanctionUser(userId, days);
			return ResponseEntity.ok("제재 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("제재 실패");
		}
	}

	@GetMapping("/reports")
	public String reportPage(Model model, HttpServletRequest request) {
		List<Report> reportList = reportService.getAllReports();
		model.addAttribute("reportList", reportList);
		model.addAttribute("currentPath", request.getRequestURI());
		return "pages/admin/admin-report";
	}

	@GetMapping("/reports/{reportId}")
	public String reportDetail(@PathVariable int reportId, Model model, HttpServletRequest request) {
		Report report = adminService.getReportById(reportId);
		model.addAttribute("report", report);
		model.addAttribute("currentPath", request.getRequestURI());
		return "pages/admin/admin-reportpage";
	}

	@GetMapping("/reports/{reportId}/content")
	public String getReportedContent(@PathVariable int reportId, Model model) {
		Report report = adminService.getReportById(reportId);
		String postHtml = "(내용을 불러올 수 없습니다)";

		if (report != null && report.getLocation() != null) {
			System.out.println("report.getLocation() = " + report.getLocation());
			String[] parts = report.getLocation().split("/");
			for (int i = 0; i < parts.length; i++) {
				System.out.println("parts[" + i + "] = " + parts[i]);
			}
			if (parts.length >= 3) {
				String type = parts[1];
				int id = Integer.parseInt(parts[2]);

				System.out.println("type = " + type + ", id = " + id);

				switch (type) {
				case "review":
					ReviewPost review = reviewPostService.getReviewPostById(id);
					if (review != null) {
						postHtml = review.getPortfolioHtml() + "<hr/>" + review.getPostHtml();
					}
					break;
				case "study":
					StudyBoard study = studyBoardService.getPostById(id);
					if (study != null) {
						postHtml = study.getPostHtml();
					}
					break;
				case "study-comment":
					StudyBoardComment sComment = studyBoardCommentService.getCommentById(id);
					if (sComment != null) {
						postHtml = sComment.getCommentsContents();
					}
					break;
				case "review-comment":
					ReviewPostComment rComment = reviewPostService.getReviewCommentById(id);
					if (rComment != null) {
						postHtml = rComment.getCommentsContents();
					}
					break;
				case "portfolio":
					Portfolio portfolio = userService.getPortfolio(id);
					if (portfolio != null) {
						postHtml = "<strong>포트폴리오 이름:</strong> " + portfolio.getPortfolioName();
					}
					break;
				default:
					postHtml = "(알 수 없는 게시물 유형입니다)";
				}
			}
		}

		System.out.println("postHtml = " + postHtml);
		model.addAttribute("postHtml", postHtml);
		return "pages/admin/admin-content-frame";
	}

	@GetMapping("/reports/{reportId}/json")
	@ResponseBody
	public Map<String, Object> getReportDetails(@PathVariable int reportId) {
		Report report = reportService.getReportById(reportId);
		Map<String, Object> response = new HashMap<>();

		response.put("reportId", report.getReportId());
		response.put("reportedUserId", report.getReportedId().getUserId());
		response.put("reportedNickname", report.getReportedId().getNickname());
		response.put("reporterNickname", report.getUserId().getNickname());
		response.put("reportContents", report.getReportContents());
		response.put("state", report.getState());
		response.put("date", report.getDate().toString());

		String[] parts = report.getLocation().split("/");
		if (parts.length >= 3) {
			String type = parts[1];
			int id = Integer.parseInt(parts[2]);

			switch (type) {
			case "study":
				StudyBoard study = studyBoardService.getPostById(id);
				response.put("postTitle", study.getTitle());
				response.put("postHtml", study.getPostHtml());
				break;
			case "review":
				ReviewPost review = reviewPostService.getReviewPostById(id);
				response.put("postTitle", review.getTitle());
				response.put("postHtml", review.getPostHtml());
				break;
			case "portfolio":
				Portfolio portfolio = userService.getPortfolio(id);
				response.put("postTitle", portfolio.getPortfolioName());
				response.put("postHtml", "[포트폴리오입니다]");
				break;
			case "study-comment":
				StudyBoardComment comment = studyBoardCommentService.getCommentById(id);
				response.put("postHtml", comment.getCommentsContents());
				break;
			default:
				response.put("postHtml", "(알 수 없는 신고 대상입니다)");
			}
		} else {
			response.put("postHtml", "(신고 대상 정보를 읽을 수 없습니다)");
		}

		return response;
	}

	@PostMapping("/reports/{reportId}/sanction")
	@ResponseBody
	public ResponseEntity<String> sanctionFromReport(@PathVariable int reportId, @RequestParam("days") int days) {
		try {
			Report report = reportService.getReportById(reportId);
			if (report == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 신고를 찾을 수 없습니다.");
			}

			int userId = report.getReportedId().getUserId();
			adminService.sanctionUser(userId, days);

			// 상태값을 '처리 완료'로 변경
			reportService.updateReportState(reportId, "처리 완료");

			return ResponseEntity.ok("신고 대상 제재 및 상태 업데이트 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신고 제재 처리 실패");
		}
	}

	// 회사 인증
	@GetMapping("/user/certifications")
	public String certificationPage(Model model, HttpServletRequest request) {
		List<User> certificationList = userService.getUsersRequestingCertification();
		model.addAttribute("userList", certificationList);
		model.addAttribute("currentPath", request.getRequestURI());
		return "pages/admin/admin-certification";
	}
}
