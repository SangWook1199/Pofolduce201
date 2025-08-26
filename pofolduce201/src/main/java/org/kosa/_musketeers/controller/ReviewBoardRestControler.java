package org.kosa._musketeers.controller;

import org.kosa._musketeers.service.ReviewBoardService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewBoardRestControler {
	
	private final ReviewBoardService reviewBoardService;
	public ReviewBoardRestControler(ReviewBoardService reviewBoardService) {
		this.reviewBoardService = reviewBoardService;
	}

	@PatchMapping("/review-post/{reviewPostId}/like")
	public int reviewPostLikePlus(@PathVariable 	int reviewPostId) {
		return reviewBoardService.plusReviewPostLike(reviewPostId);
	}
}
