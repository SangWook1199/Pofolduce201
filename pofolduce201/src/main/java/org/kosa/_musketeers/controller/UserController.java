package org.kosa._musketeers.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.kosa._musketeers.domain.MyPageComment;
import org.kosa._musketeers.domain.MyPagePost;
import org.kosa._musketeers.domain.Portfolio;
import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.domain.Verification;
import org.kosa._musketeers.service.MyBoardService;
import org.kosa._musketeers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private final MyBoardService myBoardService;

	private final UserService userService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public UserController(UserService userService, MyBoardService myBoardService) {
		this.userService = userService;
		this.myBoardService = myBoardService;
	}

	@GetMapping("/login")
	public String login() {
		return "pages/login/login";
	}

	@PostMapping("/login/processing")
	public String loginProcessing(@RequestParam String email, @RequestParam String password, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		logger.info("email:" + email + " password" + password);
		User user = userService.login(email, password);
		String page = "redirect:/login";
		if (user != null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("userId", user.getUserId());
			page = "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute("loginFailMessage", "아이디 or 비밀번호가 잘못입력되었습니다");
		}

		return page;
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		HttpSession httpSession = request.getSession();
		httpSession.removeAttribute("userId");

		return "redirect:/";
	}

	// 마이페이지로 이동 및 내정보 조회
	@GetMapping("/mypage")
	public String Mypage(HttpServletRequest request, Model model) throws IOException {

		// 세션에서 userId 가져오기
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			// 로그인 안 된 상태면 로그인 페이지로
			return "redirect:/login";
		}

		int userId = (int) session.getAttribute("userId");

		User userData = userService.getUserInformation(userId);

		// 프로필 사진이 서버에 있는 지 확인합니다. 없으면 기본 프로필을 띄웁니다.
		String fileLocation = userData.getUserImageLocation();

		String uploadDir = new ClassPathResource("static/uploads/profile/").getFile().getAbsolutePath();

		// 사용자 프로필 이미지 파일
		File profileFile = new File(uploadDir + "/" + userData.getUserId() + ".png");
		boolean hasProfileImage = profileFile.exists();
		
		//유저의 회사 정보를 가져옵니다.
		
		Verification userCompanyData = userService.getUserCompanyVerification(userId);

		// 전달
		model.addAttribute("hasProfileImage", hasProfileImage);
		model.addAttribute("userData", userData);
		model.addAttribute("userCompanyData", userCompanyData);
		System.out.println(userCompanyData);

		return "/pages/mypage/mypage-main";

	}

	// 내 게시글 조회
	@GetMapping("/mypage/mypost")
	public String getMypagePost(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			return "redirect:/login";
		}

		int userId = (int) session.getAttribute("userId");

		List<MyPagePost> myPagePostList = myBoardService.findMyPost(userId, page, size);
		int totalCount = myBoardService.countMyPost(userId);
		int totalPages = (int) Math.ceil((double) totalCount / size);
//		System.out.println("총 개수: " + totalCount);
//		System.out.println("페이지 크기: " + size);
//		System.out.println("총 페이지 수: " + totalPages);

		model.addAttribute("myPagePostList", myPagePostList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);
		return "/pages/mypage/mypage-mypost";
	}

	// 내 댓글 조회
	@GetMapping("/mypage/mycomment")
	public String getMypageComment(HttpServletRequest request, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, Model model) {
		// 세션에서 userId 가져오기
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			// 로그인 안 된 상태면 로그인 페이지로
			return "redirect:/login";
		}

		int userId = (int) session.getAttribute("userId");
		List<MyPageComment> myPageCommentList = myBoardService.findMyComment(userId, page, size);
		int totalCount = myBoardService.countMyComment(userId);
		int totalPages = (int) Math.ceil((double) totalCount / size);

		model.addAttribute("myPageCommentList", myPageCommentList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);
		System.out.println(myPageCommentList);

		return "/pages/mypage/mypage-mycomment";

	}

	// 내(가 작성한) 첨삭 조회
	@GetMapping("/mypage/myreview")
	public String getMypageReview(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, HttpServletRequest request, Model model) {

		// 세션에서 userId 가져오기
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			// 로그인 안 된 상태면 로그인 페이지로
			return "redirect:/login";
		}

		int userId = (int) session.getAttribute("userId");
		List<Review> myPageReviewList = myBoardService.findMyReview(userId, page, size);
		int totalCount = myBoardService.countMyReview(userId);
		int totalPages = (int) Math.ceil((double) totalCount / size);

		model.addAttribute("myPageReviewList", myPageReviewList);
		System.out.println(myPageReviewList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);

		return "/pages/mypage/mypage-myreview";

	}

	// 내 정보 수정 (닉네임, 이메일)
	@PostMapping("/mypage/update")
	@ResponseBody // 중요! redirect 없이 메시지 바로 반환
	public String updateUserInfomation(HttpServletRequest request, String nickname, String email) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			return "로그인이 필요합니다.";
		}
		int userId = (int) session.getAttribute("userId");

		boolean update = userService.updateUserInfomation(userId, nickname, email);

		if (!update) {
			return "닉네임 또는 이메일이 중복입니다.";
		} else {
			return "수정이 완료되었습니다";

		}
	}

	// 내 이력서 페이지
	@GetMapping("/mypage/myportfolio")
	public String getMyPortfolio(Model model, @SessionAttribute("userId") Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		List<Portfolio> portfolio = userService.getPortfolioList(userId);
		Integer pofId = userService.getRepPortfolio(userId);
		model.addAttribute("portfolios", portfolio);
		model.addAttribute("repId", pofId);
		return "/pages/mypage/mypage-myportfolio";
	}

	// 이력서 업로드
	@PostMapping("/mypage/myportfolio/upload")
	public String uploadPortfolio(@RequestParam("file") MultipartFile file,
			@RequestParam("portfolioName") String portfolioName, @SessionAttribute("userId") int userId,
			RedirectAttributes redirectAttributes) throws IOException {
		// 파일이 비어있는지 체크
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "파일을 선택해 주세요.");
			return "redirect:/mypage/myportfolio";
		}

		try {
			User user = new User();
			user.setUserId(userId);
			Integer repId = userService.getRepPortfolio(userId);
			userService.createPortfolio(file, portfolioName, user);

			if (repId == null) {
				int portId = userService.getPortfolioById(userId);
				userService.setFirstRepPortfolio(userId, portId);
			}
			redirectAttributes.addFlashAttribute("successMessage", "포트폴리오가 성공적으로 업로드되었습니다.");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드에 실패했습니다. 다시 시도해 주세요.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "업로드 중 오류가 발생했습니다.");
		}
		return "redirect:/mypage/myportfolio";
	}

	// 회원 탈퇴
	@PostMapping("/mypage/delete")
	public String deleteAccount(HttpServletRequest request) {

		// 세션에서 userId 가져오기
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			// 로그인 안 된 상태면 로그인 페이지로
			return "redirect:/login";
		}

		int userId = (int) session.getAttribute("userId");

		userService.deleteAccount(userId);

		return "redirect:/login";

	}

	// 이력서 상세 페이지
	@GetMapping("/mypage/myportfolio/{portfolioId}")
	public String getPortfolio(@PathVariable int portfolioId, @SessionAttribute("userId") int userId, Model model) {
		Portfolio portfolio = userService.getPortfolio(portfolioId);

		model.addAttribute("portfolio", portfolio);

		return "/pages/mypage/mypage-myportfolio-detail";
	}

	// 대표 이력서 설정
	@PostMapping("/mypage/myportfolio/{portfolioId}/rep")
	public String setRepPortfolio(@PathVariable int portfolioId, @SessionAttribute("userId") int userId, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Integer pofId = userService.getRepPortfolio(userId);
			Portfolio portfolio = userService.getPortfolio(portfolioId);

			if (portfolio == null) {
				return "redirect:/mypage/myportfolio?error=notfound";
			}

			if (portfolio.getUserId().getUserId() != userId) {
				return "redirect:/mypage/myportfolio?error=아이디가 일치하지 않습니다.";
			}

			if (pofId != null && pofId.equals(portfolioId)) {
				redirectAttributes.addFlashAttribute("alertMessage", "이미 대표 이력서입니다.");
				return "redirect:/mypage/myportfolio/" + portfolioId;
			}

			model.addAttribute("portfolio", portfolio);
			// 대표 이력서로 설정
			userService.setRepPortfolio(userId, portfolioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mypage/myportfolio";
	}

	// 이력서 삭제
	@PostMapping("/mypage/myportfolio/{portfolioId}/delete")
	public String deletePortfolio(@PathVariable int portfolioId, @SessionAttribute("userId") int userId,
			RedirectAttributes redirectAttributes) {
		try {
			Integer pofId = userService.getRepPortfolio(userId);
			// 나중에 ajax 처리
			if (pofId != null && pofId == portfolioId) {
				redirectAttributes.addFlashAttribute("errorMessage", "대표 이력서는 삭제할 수 없습니다.");
				return "redirect:/mypage/myportfolio/" + portfolioId;
			}
			
			// 대표 이력서가 아니라면 삭제
			userService.deletePortfolio(portfolioId);
			redirectAttributes.addFlashAttribute("successMessage", "이력서가 삭제되었습니다.");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mypage/myportfolio";
	}

	// 유저의 프로필을 업로드하는 메소드입니다.
	@PostMapping("/mypage/profile")
	public String updateProfile(HttpServletRequest request, @RequestParam("file") MultipartFile file)
			throws IOException {
		// 세션에서 userId 가져오기
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			// 로그인 안 된 상태면 로그인 페이지로
			return "redirect:/login";
		}

		int userId = (int) session.getAttribute("userId");

		userService.updateProfile(file, userId);

		return "redirect:/mypage";

	}
	
	//
	@PostMapping("/mypage/company")
	public String insertCompany(HttpServletRequest request, @RequestParam("file") MultipartFile file,@RequestParam("company") String company)
			throws IOException {
		// 세션에서 userId 가져오기
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			// 로그인 안 된 상태면 로그인 페이지로
			return "redirect:/login";
		}

		int userId = (int) session.getAttribute("userId");

		userService.insertCompany(file, userId, company);

		return "redirect:/mypage";

	}
	

	// 다른 유저 페이지
	@GetMapping("/userpage/{userId}")
	public String userPage(@PathVariable int userId, Model model) {
		try {
			User userData = userService.getUserInformation(userId);
			Integer repPortId = userService.getRepPortfolio(userId);
			Portfolio portfolio = null;
			if (repPortId != null) {
				portfolio = userService.getPortfolio(repPortId);
			}
			
			model.addAttribute("portfolio", portfolio);
			model.addAttribute("userData", userData);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "pages/mypage/userpage";
	}

}
