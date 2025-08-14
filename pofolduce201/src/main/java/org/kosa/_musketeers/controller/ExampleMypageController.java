package org.kosa._musketeers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 마이페이지 임시 컨트롤러 입니다. 추후 삭제합니다.
 */

@Controller
public class ExampleMypageController {

	// 임시 메소드
	@GetMapping("/mypage")
	public String Mypage() {
		return "/pages/mypage/mypage-main";

	}

	// 임시 메소드
	@GetMapping("/mypage/mypost")
	public String MypagePost() {
		return "/pages/mypage/mypage-mypost";

	}

	// 임시 메소드
	@GetMapping("/mypage/mycomment")
	public String MypageComment() {
		return "/pages/mypage/mypage-mycomment";

	}

	// 임시 메소드
	@GetMapping("/mypage/myreview")
	public String MypageReview() {
		return "/pages/mypage/mypage-myreview";

	}
}
