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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReviewBoardController {

	private ReviewBoardService reviewBoardService;
	private UserService userService;

	private ReviewBoardController(ReviewBoardService reviewBoardService, UserService userService) {
		this.reviewBoardService = reviewBoardService;
		this.userService = userService;
	}

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

	@GetMapping("/review/post{reviewPostId}")
	public String reviewViewPost(@RequestParam int reviewPostId, @RequestParam(defaultValue="1") int currentCommentPage, Model model,HttpServletRequest request) {
		
		int commentCount = 5;
		int totalCommentPage = reviewBoardService.getTotalReviewCommentCount(reviewPostId) / commentCount + 1;
		ReviewPost reviewPost = reviewBoardService.viewPost(reviewPostId);
		List<ReviewPostComment> commentList = reviewBoardService.loadReviewPostCommentList(reviewPostId, (currentCommentPage - 1) * commentCount, commentCount);
		
		System.out.println(reviewPost.getUser());
		System.out.println(commentList);
		
		model.addAttribute("totalCommentPages", totalCommentPage);
		model.addAttribute("currentCommentPage", currentCommentPage);
		model.addAttribute("reviewPost", reviewPost);
		model.addAttribute("commentsList", commentList);
		model.addAttribute("currentUri", request.getRequestURI());
		return "pages/review/review-post";
	}

	@GetMapping("/review/write")
	public String reviewWritePost() {
		return "pages/review/review-post-write";
	}

	@PostMapping("/review/write/save")
	public String saveReviewPost(@ModelAttribute ReviewPost reviewPost, HttpServletRequest request) {

		reviewPost.setUser(new User((Integer)request.getSession().getAttribute("userId")));
		reviewBoardService.createPost(reviewPost);
		return "redirect:/review-post";
	}

	@GetMapping("/review/post/{reviewPostId}/edit")
	public String editReviewPost(@PathVariable int reviewPostId, Model model) {

		ReviewPost reviewPost = reviewBoardService.getReviewPostById(reviewPostId);
		model.addAttribute("reviewPost", reviewPost);
		return "pages/review/review-post-edit";
	}

	@PostMapping("/review/edit/save")
	public String saveEditReviewPost(@ModelAttribute ReviewPost reviewPost) {
		reviewBoardService.editReviewPost(reviewPost);
		return "redirect:/review/post?reviewPostId=" + reviewPost.getReviewPostId();
	}

	@PostMapping("/review/post/{reviewPostId}/delete")
	public String deleteReviewPost(@PathVariable int reviewPostId) {
		reviewBoardService.deleteReviewPost(reviewPostId);
		return "redirect:/review-post";
	}

	@PostMapping("/review/comment")
	public String createComment(@RequestParam int userId, @RequestParam int reviewPostId,
			@RequestParam String comment) {
		System.out.println(userId);
		System.out.println(reviewPostId);
		System.out.println(comment);

		reviewBoardService.writeComment(userId, reviewPostId, comment);
		return "redirect:/review/post?reviewPostId=" + reviewPostId;
	}

	@PostMapping("/review/comment/{reviewCommentId}/delete")
	public String deleteComment(@PathVariable int reviewCommentId, @RequestParam int reviewPostId) {
		reviewBoardService.deleteComment(reviewCommentId);
		return "redirect:/review/post?reviewPostId=" + Integer.toString(reviewPostId);
	}

	@PostMapping("/review/comment/{reviewCommentId}/edit")
	public String editComment(@PathVariable int reviewCommentId, @RequestParam String commentsContents,
			@RequestParam int reviewPostId) {
		reviewBoardService.modifyComment(reviewCommentId, commentsContents);
		return "redirect:/review/post?reviewPostId=" + Integer.toString(reviewPostId);
	}

	@GetMapping("/my-portfolio")
	public String selectPortfolio(HttpServletRequest request, Model model) {
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		List<Portfolio> myPortfolioList = userService.getPortfolioList(userId);
		model.addAttribute("myPortfolioList", myPortfolioList);

		Integer repFolioId = userService.getRepPortfolio(userId);
		model.addAttribute("repFolioId", repFolioId);
		return "pages/review/review-select-folio";
	}

	@GetMapping("/my-portfolio/{portfolioId}")
	public String confirmSelectedPortfolio(@PathVariable int portfolioId, Model model) {
		Portfolio portfolio = userService.getPortfolio(portfolioId);
		model.addAttribute("portfolio", portfolio);
		return "pages/review/review-select-folio-confirm";
	}

	@GetMapping("/my-portfolio/{portfolioId}/html-converted")
	@ResponseBody
	public String sendConvertedPortfolioByFetch(@PathVariable int portfolioId) {
		String convertedHtml = reviewBoardService.convertPdfToHtml(portfolioId);
		return convertedHtml;
	}
}
