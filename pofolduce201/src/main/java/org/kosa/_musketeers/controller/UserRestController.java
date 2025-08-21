package org.kosa._musketeers.controller;

import java.util.HashMap;
import java.util.Map;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.ReviewBoardService;
import org.kosa._musketeers.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
	private final UserService userService;
	private final ReviewBoardService reviewBoardService;

	public UserRestController(UserService userService, ReviewBoardService reviewBoardService) {
		super();
		this.userService = userService;
		this.reviewBoardService = reviewBoardService;
	}

	// 다른 유저 팝오버창
	@GetMapping("/api/user/{userId}")
	public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable int userId) {
		User user = userService.getUserInformation(userId);
		int countReview = reviewBoardService.getTotalReviewPostCountById(userId);
		int countPortfolio = userService.getTotalPortfolioCountById(userId);
		Map<String, Object> result = new HashMap<>();
		result.put("userId", userId);
		result.put("nickname", user.getNickname());
		result.put("point", user.getPoint());
		result.put("countReview", countReview);
		result.put("countPortfolio", countPortfolio);
		return ResponseEntity.ok(result);
	}
}
