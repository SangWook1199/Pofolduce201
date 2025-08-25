package org.kosa._musketeers.controller;

import java.util.List;

import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
public class ReviewController {
	
	private final ReviewService reviewService;
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping("/review")
	public ResponseEntity<String> postReview(@ModelAttribute Review review, @SessionAttribute(name="userId") Integer userId) {
		
		review.setUser(new User(userId));
		System.out.println(review);
		reviewService.addReview(review);
		return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
	}
	
	@GetMapping("/review/{reviewPostId}")
	public List<Review> getReview(@PathVariable int reviewPostId) {
		return reviewService.findReviewsByReviewPostId(reviewPostId);
	}
	
	@DeleteMapping("/review/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable int reviewId){
		reviewService.removeReview(reviewId);
		return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
	}
}
