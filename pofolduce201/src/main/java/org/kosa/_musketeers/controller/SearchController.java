package org.kosa._musketeers.controller;

import java.util.List;
import java.util.Map;

import org.kosa._musketeers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 검색 조회의 controller 입니다.
 */
@Controller
public class SearchController {

	private final UserService userService;

	public SearchController(UserService userService) {
		super();
		this.userService = userService;
	}

	// 검색 컨트롤러 입니다.
	@GetMapping("/search")
	// RequestParam(해당 input의 name) 으로 검색어를 받아옵니다.
	public String search(@RequestParam("search") String search, Model model, @RequestParam(defaultValue = "1") int reviewPage,
			@RequestParam(defaultValue = "5") int reviewSize, @RequestParam(defaultValue = "1") int studyPage,
			@RequestParam(defaultValue = "5") int studySize) {
		
		List<Map<String, Object>> reviewList = userService.getReviewSearchResult(search, reviewPage, reviewSize);
		List<Map<String, Object>> studyList = userService.getStudySearchResult(search, studyPage, studySize);
		
		int reviewTotalCount = userService.countReviewResult(search);
		int reviewTotalPages = (int) Math.ceil((double) reviewTotalCount / reviewSize);
		int studyTotalCount = userService.countStudyResult(search);
		int studyTotalPages = (int) Math.ceil((double) studyTotalCount / studySize);

		model.addAttribute("search", search);
		
		model.addAttribute("reviewList", reviewList);
		//System.out.println(reviewList);
		model.addAttribute("reviewCurrentPage", reviewPage);
		model.addAttribute("reviewTotalPages", reviewTotalPages);
		model.addAttribute("reviewSize", reviewSize);
		
		model.addAttribute("studyList", studyList);
		//System.out.println(studyList);
		model.addAttribute("studyCurrentPage", studyPage);
		model.addAttribute("studyTotalPages", studyTotalPages);
		model.addAttribute("studySize", studySize);
		
		
		return "/pages/search/search";
	}
	
	//첨삭 게시판 검색 결과 입니다.
	@GetMapping("/search/review")
	public String searchReview(@RequestParam("search") String search, Model model, @RequestParam(defaultValue = "1") int reviewPage,
			@RequestParam(defaultValue = "20") int reviewSize) {
		List<Map<String, Object>> reviewList = userService.getReviewSearchResult(search, reviewPage, reviewSize);
		int reviewTotalCount = userService.countReviewResult(search);
		int reviewTotalPages = (int) Math.ceil((double) reviewTotalCount / reviewSize);
		
		
		model.addAttribute("search", search);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("reviewCurrentPage", reviewPage);
		model.addAttribute("reviewTotalPages", reviewTotalPages);
		model.addAttribute("reviewSize", reviewSize);
		
		return "/pages/search/search-review";
	}
	
	//스터디 게시판 검색 결과 입니다.
	@GetMapping("/search/study")
	public String searchStudy(@RequestParam("search") String search, Model model,  @RequestParam(defaultValue = "1") int studyPage,
			@RequestParam(defaultValue = "20") int studySize) {
		
		List<Map<String, Object>> studyList = userService.getStudySearchResult(search, studyPage, studySize);
		int studyTotalCount = userService.countStudyResult(search);
		int studyTotalPages = (int) Math.ceil((double) studyTotalCount / studySize);
		
		model.addAttribute("search", search);
		model.addAttribute("studyList", studyList);
		model.addAttribute("studyCurrentPage", studyPage);
		model.addAttribute("studyTotalPages", studyTotalPages);
		model.addAttribute("studySize", studySize);

		return "/pages/search/search-study";
	}
	
	//채용 공고 검색 결과 입니다.
	@GetMapping("/search/recruit")
	public String searchRecruit() {
		return "/pages/search/search-recruit";
	}

}
