package org.kosa._musketeers.controller;

import java.util.List;

import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

//리뷰에 대한 클라이언트의 요청을 처리하는 컨트롤러입니다.
@RestController
public class ReviewController {
	
	private final ReviewService reviewService;
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	// 첨삭 생성 요청을 처리하는 메서드입니다.
	// client로부터 review 객체를 전송받고, session의 userId를 받아와 서비스에 전달하여 요청을 처리합니다.
	@PostMapping("/review")
	public ResponseEntity<String> postReview(@ModelAttribute Review review, @SessionAttribute(name="userId") Integer userId) {
		reviewService.addReview(review, userId);
		return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
	}
	
	// 리뷰 조회 요청을 처리하는 메서드입니다.
	// url의 pathVaraible을 통하여 첨삭 게시물의 id를 받아와 ReviewService에게 전달하고,
	// ReviewService가 반환하는 첨삭 리스트를 전송합니다.
	@GetMapping("/review/{reviewPostId}")
	public List<Review> getReview(@PathVariable int reviewPostId) {
		return reviewService.findReviewsByReviewPostId(reviewPostId);
	}
	
	// 리뷰 수정 요청을 처리하는 메서드입니다.
	// url의 pathVariable을 통하여 reviewId를 받아와, ReviewService에게 전달하여 내용을 수정합니다.
	@PatchMapping("/review/{reviewId}")
	public ResponseEntity<String> patchReview(@PathVariable int reviewId, @RequestBody String reviewContents){
		reviewService.modifyReview(reviewId, reviewContents);
		return ResponseEntity.ok("리뷰가 성공적으로 업데이트되었습니다.");
	}
	
	// 리뷰 삭제 요청을 처리하는 메서드입니다.
	// url의 pathVariable을 통하여 reviewId를 받아와 ReviewService에게 전달하여 삭제를 처리합니다.
	@DeleteMapping("/review/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable int reviewId){
		reviewService.removeReview(reviewId);
		return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
	}
	
	// 리뷰 좋아요를 처리하는 메서드입니다.
	// url의 PathVariable을 통하여 reviewId를 받아와 ReviewService에게 전달하여 좋아요를 처리합니다.
	@PatchMapping("/review/{reviewId}/like")
	public ResponseEntity<String> likeReview(@PathVariable Integer reviewId, @RequestBody String reviewUserId){
		reviewService.plusReviewLike(reviewId, Integer.parseInt(reviewUserId));
		return ResponseEntity.ok("리뷰의 좋아요가 성공적으로 처리되었습니다.");
	}
}
