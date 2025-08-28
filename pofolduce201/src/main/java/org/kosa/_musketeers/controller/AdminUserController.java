package org.kosa._musketeers.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kosa._musketeers.domain.*;
import org.kosa._musketeers.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	public String userDetail(@PathVariable int userId, Model model, HttpServletRequest request, HttpSession session) {
		// 1. userData를 가져와 모델에 추가
		User user = userService.getUserById(userId);
		model.addAttribute("userData", user);

		// 2. 세션에서 로그인한 사용자의 ID를 가져와 모델에 추가
		User loginUser = (User) session.getAttribute("loginUser");
	    if (loginUser != null) {
	        // 현재 페이지의 유저 ID가 로그인한 유저 ID와 같은지 확인
	        boolean isSelfPage = (loginUser.getUserId() == userId);
	        model.addAttribute("isSelfPage", isSelfPage);
	    }

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

		if (report != null) {
			// 신고된 유저의 정보를 가져와서 제재 상태를 확인합니다.
			int reportedUserId = report.getReportedId().getUserId();
			User reportedUser = userService.getUserById(reportedUserId);

			// 모델에 유저 정보를 추가하여 HTML에서 제재 상태를 확인할 수 있게 합니다.
			model.addAttribute("reportedUser", reportedUser);
		}

		model.addAttribute("report", report);
		model.addAttribute("currentPath", request.getRequestURI());
		return "pages/admin/admin-reportpage";
	}

	@GetMapping(value = "/reports/{reportId}/content", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getReportedContent(@PathVariable int reportId) {
		Report report = adminService.getReportById(reportId);
		String postHtml = "<p>(내용을 불러올 수 없습니다)</p>"; // 기본값

		if (report != null && report.getLocation() != null) {
			String[] parts = report.getLocation().split("/");

			if (parts.length >= 3) {
				String type = parts[1];
				int id = Integer.parseInt(parts[2]);

				switch (type) {
				case "review":
					ReviewPost review = reviewPostService.getReviewPostById(id);
					if (review != null) {
						postHtml = "<div>" + review.getPortfolioHtml() + "<hr/>" + review.getPostHtml() + "</div>";
					}
					break;
				case "study":
					StudyBoard study = studyBoardService.getPostById(id);
					if (study != null) {
						postHtml = "<div>" + study.getPostHtml() + "</div>";
					}
					break;
				case "study-comment":
					StudyBoardComment sComment = studyBoardCommentService.getCommentById(id);
					if (sComment != null) {
						postHtml = "<p>" + sComment.getCommentsContents() + "</p>";
					}
					break;
				case "review-comment":
					ReviewPostComment rComment = reviewPostService.getReviewCommentById(id);
					if (rComment != null) {
						postHtml = "<p>" + rComment.getCommentsContents() + "</p>";
					}
					break;
				case "portfolio":
					Portfolio portfolio = userService.getPortfolio(id);
					if (portfolio != null) {
						postHtml = "<strong>포트폴리오 이름:</strong> " + portfolio.getPortfolioName();
					}
					break;
				default:
					postHtml = "<p>(알 수 없는 게시물 유형입니다)</p>";
				}
			}
		}

		// 가져온 HTML 콘텐츠를 <body> 태그 내에 포함시켜 반환합니다.
		return "<html><body>" + postHtml + "</body></html>";
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
		List<Verification> verificationList = adminService.getAllVerifications();
		model.addAttribute("verificationList", verificationList);
		model.addAttribute("currentPath", request.getRequestURI());
		return "pages/admin/admin-certification";
	}

	// 회사 인증 상태 업데이트시
	// AdminUserController.java

	@PostMapping("/user/certifications/{userId}/status")
	@ResponseBody
	public ResponseEntity<String> updateVerificationStatus(@PathVariable int userId,
			@RequestParam("status") String status) {
		// 1. 인증 상태 업데이트 (verification 테이블)
		adminService.updateVerificationStatus(userId, status);

		System.out.println("인증 상태 업데이트 요청: userId=" + userId + ", status=" + status);

		// 2. 만약 상태가 '완료'라면, 유저 정보도 업데이트
		if ("완료".equals(status)) {
			// Verification 정보를 다시 가져와서 회사 이름을 얻음
			Verification verification = userService.getUserCompanyVerification(userId);

			// verification 객체 확인
			System.out.println("가져온 Verification 객체: " + verification);

			if (verification != null) {
				String companyName = verification.getCompanyName();

				// companyName 변수 값 확인
				System.out.println("가져온 회사 이름: " + companyName);

				// User 테이블의 companyCertification과 companyName을 업데이트
				userService.updateUserCompanyInfo(userId, "yes", companyName);
			}
		}
		// 3. 만약 상태가 '반려'라면, 유저 정보 초기화
		else if ("반려".equals(status)) {
			userService.updateUserCompanyInfo(userId, "no", null);
		}

		return ResponseEntity.ok("상태 변경 성공");
	}

	// 팝업 페이지 열기
	@GetMapping("/user/certificationpage")
	public String certificationDetailPopup(@RequestParam("userId") int userId, Model model) {
		model.addAttribute("userId", userId);
		return "pages/admin/admin-certificationpage";
	}

	// 인증 상세 JSON 제공
	@GetMapping("/user/certification/{userId}/json")
	@ResponseBody
	public Map<String, Object> getCertificationJson(@PathVariable int userId) {
		// 1. Verification 정보 가져오기
		Verification verification = userService.getUserCompanyVerification(userId);

		// 2. userId로 User 정보(닉네임) 가져오기
		User user = userService.getUserById(userId);

		Map<String, Object> result = new HashMap<>();

		// 3. 닉네임, 회사명, 상태 등 필요한 정보 담기
		result.put("nickname", user.getNickname()); // 수정된 부분
		result.put("company", verification.getCompanyName());
		result.put("date", verification.getDate().toString());
		result.put("state", verification.getState());
		result.put("imageUrl", "/uploads/company/" + userId + ".png");

		return result;
	}

}
