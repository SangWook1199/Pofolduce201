package org.kosa._musketeers.controller;

import java.util.List;

import org.kosa._musketeers.domain.ReviewPost;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.ReviewBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReviewBoardController {
	
	private ReviewBoardService reviewBoardService;
	private ReviewBoardController(ReviewBoardService reviewBoardService) {
		this.reviewBoardService = reviewBoardService;
	}

	@GetMapping("/review")
	public String review(@RequestParam(defaultValue = "1") int page, Model model) {
		
		int pagePostCount = 15;
		int totalPage = reviewBoardService.getTotalReviewPostCount() / pagePostCount + 1;
		List<ReviewPost> bestReviewPostList = reviewBoardService.getBestReviewPostList();
		List<ReviewPost> reviewPostList = reviewBoardService.getReviewPostList(page);
		
		model.addAttribute("totalPages", totalPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("reviewPostList",reviewPostList);
		model.addAttribute("bestReviewPostList", bestReviewPostList);
		return "pages/review/review-board";
	}
	
	@GetMapping("/review/post{reviewPostId}")
	public String reviewViewPost(@RequestParam int reviewPostId, Model model) {
		ReviewPost reviewPost = reviewBoardService.viewPost(reviewPostId);
		model.addAttribute("reviewPost", reviewPost);
		return "pages/review/review-post";
	}
	
	
	@GetMapping("/review/write")
	public String reviewWritePost() {
		return "pages/review/review-write";
	}
	
	@PostMapping("/review/write/save")
	public String reviewSavePost(@ModelAttribute ReviewPost reviewPost, HttpServletRequest request) {

		reviewPost.setUser(new User((Integer)request.getSession().getAttribute("userId")));
		reviewBoardService.createPost(reviewPost);
		return "redirect:/review";
	}
}
