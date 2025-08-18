package org.kosa._musketeers.controller;

import java.util.List;

import org.kosa._musketeers.domain.MyPageComment;
import org.kosa._musketeers.domain.MyPagePost;
import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.MyBoardService;
import org.kosa._musketeers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private final MyBoardService myBoardService;

	UserService userService;

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
			page = "redirect:/home";
		} else {
			redirectAttributes.addFlashAttribute("loginFailMessage", "아이디 or 비밀번호가 잘못입력되었습니다");
		}

		return page;
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession();
		httpSession.removeAttribute("userId");
		
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String home() {
		return "index";
	}
	
	// 마이페이지로 이동 및 내정보 조회
		@GetMapping("/mypage")
		public String Mypage(HttpServletRequest request, Model model ) {
			
			// 세션에서 userId 가져오기
		    HttpSession session = request.getSession(false);
		    if (session == null || session.getAttribute("userId") == null) {
		        // 로그인 안 된 상태면 로그인 페이지로
		        return "redirect:/login";
		    }
		    
		    int userId = (int) session.getAttribute("userId");
		    User userData = userService.getUserInformation(userId);
		    model.addAttribute("userData", userData);
			
			return "/pages/mypage/mypage-main";

		}

	// 내 게시글 조회
	@GetMapping("/mypage/mypost")
	public String getMypagePost(
	        HttpServletRequest request,
	        Model model,
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "10") int size
	) {
	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("userId") == null) {
	        return "redirect:/login";
	    }

	    int userId = (int) session.getAttribute("userId");

	    List<MyPagePost> myPagePostList = myBoardService.findMyPost(userId, page, size);
	    int totalCount = myBoardService.countMyPost(userId);
	    int totalPages = (int) Math.ceil((double) totalCount / size);

	    model.addAttribute("myPagePostList", myPagePostList);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("size", size);
	    return "/pages/mypage/mypage-mypost";
	}
	
	//내 댓글 조회
		@GetMapping("/mypage/mycomment")
		public String getMypageComment(HttpServletRequest request,@RequestParam(defaultValue = "1") int page,
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

			
			return "/pages/mypage/mypage-mycomment";

		}

		//내(가 작성한) 첨삭 조회
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
			    model.addAttribute("currentPage", page);
			    model.addAttribute("totalPages", totalPages);
			    model.addAttribute("size", size);
	
			
			
			return "/pages/mypage/mypage-myreview";

		}
		
		//내 정보 수정 (닉네임, 이메일)
		@PostMapping("/mypage/update")
		public String updateUserInfomation(HttpServletRequest request, String nickname, String email){
			// 세션에서 userId 가져오기
		    HttpSession session = request.getSession(false);
		    if (session == null || session.getAttribute("userId") == null) {
		        // 로그인 안 된 상태면 로그인 페이지로
		        return "redirect:/login";
		    }
		    int userId = (int) session.getAttribute("userId");
		    
			boolean update = userService.updateUserInfomation(userId, nickname, email);
			
			if(update == false) {
				
				return "redirect:/mypage"; // URL로 리다이렉트
			}
			else {
				return "redirect:/mypage"; // URL로 리다이렉트
			}
			
			
			
		}
		
		
}
