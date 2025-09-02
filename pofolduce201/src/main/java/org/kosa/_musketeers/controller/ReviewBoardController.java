package org.kosa._musketeers.controller;

import java.util.List;

import org.kosa._musketeers.domain.Portfolio;
import org.kosa._musketeers.domain.ReviewPost;
import org.kosa._musketeers.domain.ReviewPostComment;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.ReviewBoardService;
import org.kosa._musketeers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReviewBoardController {

	private ReviewBoardService reviewBoardService;
	private UserService userService;

	private ReviewBoardController(ReviewBoardService reviewBoardService, UserService userService) {
		this.reviewBoardService = reviewBoardService;
		this.userService = userService;
	}

	// 첨삭게시판을 조회
	@GetMapping("/review-post")
	public String review(@RequestParam(defaultValue = "1") int page, Model model, HttpServletRequest request) {

		int pagePostCount = 15;
		int totalPage = reviewBoardService.getTotalReviewPostCount() / pagePostCount + 1;
		List<ReviewPost> bestReviewPostList = reviewBoardService.getBestReviewPostList();
		List<ReviewPost> reviewPostList = reviewBoardService.getReviewPostList(page);

		model.addAttribute("totalPages", totalPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("reviewPostList", reviewPostList);
		model.addAttribute("bestReviewPostList", bestReviewPostList);

		model.addAttribute("currentUri", request.getRequestURI());

		return "pages/review/review-post-board";
	}

	// 첨삭 게시물 조회
	@GetMapping("/review/post{reviewPostId}")
	public String reviewViewPost(@RequestParam int reviewPostId,
			@RequestParam(defaultValue = "1") int currentCommentPage, Model model, HttpServletRequest request,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}

		int commentCount = 5;
		int totalCommentCount = reviewBoardService.getTotalReviewCommentCount(reviewPostId); 
		int totalCommentPage = totalCommentCount / commentCount;
		if(totalCommentCount % commentCount > 0)
			totalCommentPage += 1;
		
		ReviewPost reviewPost = reviewBoardService.viewPost(reviewPostId);
		List<ReviewPostComment> commentList = reviewBoardService.loadReviewPostCommentList(reviewPostId,
				(currentCommentPage - 1) * commentCount, commentCount);
		User user = userService.getUserById(userId);
		String userImageLocation = user.getUserImageLocation();
		String userType = user.getUserType();

		model.addAttribute("userImageLocation", userImageLocation);
		model.addAttribute("totalCommentPages", totalCommentPage);
		model.addAttribute("currentCommentPage", currentCommentPage);
		model.addAttribute("reviewPost", reviewPost);
		model.addAttribute("commentsList", commentList);
		model.addAttribute("userType", userType);
		model.addAttribute("currentUri", request.getRequestURI());
		return "pages/review/review-post";
	}

	// 첨삭 게시물 작성 페이지
	@GetMapping("/review/write")
	public String reviewWritePost(@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		return "pages/review/review-post-write";
	}

	// 첨삭 게시물 작성 요청 처리
	@PostMapping("/review/write/save")
	public String saveReviewPost(@ModelAttribute ReviewPost reviewPost, HttpServletRequest request,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}

		reviewPost.setUser(new User((Integer) request.getSession().getAttribute("userId")));
		reviewBoardService.createPost(reviewPost);
		return "redirect:/review-post";
	}

	// 첨삭 게시물 수정 페이지
	@GetMapping("/review/post/{reviewPostId}/edit")
	public String editReviewPost(@PathVariable int reviewPostId, Model model,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}

		ReviewPost reviewPost = reviewBoardService.getReviewPostById(reviewPostId);
		model.addAttribute("reviewPost", reviewPost);
		return "pages/review/review-post-edit";
	}

	// 첨삭 게시물 수정 요청 처리
	@PostMapping("/review/edit/save")
	public String saveEditReviewPost(@ModelAttribute ReviewPost reviewPost,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		reviewBoardService.editReviewPost(reviewPost);
		return "redirect:/review/post?reviewPostId=" + reviewPost.getReviewPostId();
	}

	// 첨삭 게시물 삭제 요청 처리
	@PostMapping("/review/post/{reviewPostId}/delete")
	public String deleteReviewPost(@PathVariable int reviewPostId,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		reviewBoardService.deleteReviewPost(reviewPostId);
		return "redirect:/review-post";
	}

	// 첨삭 게시물 댓글 작성 요청 처리
	@PostMapping("/review/comment")
	public String createComment(@RequestParam int reviewPostId, @RequestParam String comment,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		reviewBoardService.writeComment(userId, reviewPostId, comment);
		return "redirect:/review/post?reviewPostId=" + reviewPostId;
	}

	// 첨삭 게시물 댓글 삭제 요청 처리
	@PostMapping("/review/comment/{reviewCommentId}/delete")
	public String deleteComment(@PathVariable int reviewCommentId, @RequestParam int reviewPostId,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		reviewBoardService.deleteComment(reviewCommentId);
		return "redirect:/review/post?reviewPostId=" + Integer.toString(reviewPostId);
	}

	// 첨삭 게시물 댓글 수정 요청 처리
	@PostMapping("/review/comment/{reviewCommentId}/edit")
	public String editComment(@PathVariable int reviewCommentId, @RequestParam String commentsContents,
			@RequestParam int reviewPostId, @SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		reviewBoardService.modifyComment(reviewCommentId, commentsContents);
		return "redirect:/review/post?reviewPostId=" + Integer.toString(reviewPostId);
	}

	// 나의 포트폴리오 리스트 표시
	@GetMapping("/my-portfolio")
	public String selectPortfolio(HttpServletRequest request, Model model,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		List<Portfolio> myPortfolioList = userService.getPortfolioList(userId);
		model.addAttribute("myPortfolioList", myPortfolioList);

		Integer repFolioId = userService.getRepPortfolio(userId);
		model.addAttribute("repFolioId", repFolioId);
		return "pages/review/review-select-folio";
	}

	// 포트폴리오 상세 페이지
	@GetMapping("/my-portfolio/{portfolioId}")
	public String confirmSelectedPortfolio(@PathVariable int portfolioId, Model model,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		Portfolio portfolio = userService.getPortfolio(portfolioId);
		model.addAttribute("portfolio", portfolio);
		return "pages/review/review-select-folio-confirm";
	}

	// 포트폴리오 html 변환 처리하여 전송
	@GetMapping("/my-portfolio/{portfolioId}/html-converted")
	@ResponseBody
	public String sendConvertedPortfolioByFetch(@PathVariable int portfolioId) {
		String convertedHtml = reviewBoardService.convertPdfToHtml(portfolioId);
		return convertedHtml;
	}
	
	// 첨삭 좋아요 처리
	@PatchMapping("/review-post/{reviewPostId}/like")
	public int reviewPostLikePlus(@PathVariable 	int reviewPostId) {
		return reviewBoardService.plusReviewPostLike(reviewPostId);
	}
}
