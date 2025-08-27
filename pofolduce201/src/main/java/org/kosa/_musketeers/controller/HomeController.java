package org.kosa._musketeers.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kosa._musketeers.service.RecruitService;
import org.kosa._musketeers.service.ReviewBoardService;
import org.kosa._musketeers.service.StudyBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//메인 화면을 다루는 컨트롤러 입니다.
public class HomeController {

	// 스터디 게시글을 조회하는 서비스를 가져옵니다.
	private final StudyBoardService studyBoardService;
	private final RecruitService recruitService;
	private final ReviewBoardService reviewBoardService;

	public HomeController(StudyBoardService studyBoardService, RecruitService recruitService, ReviewBoardService reviewBoardService) {
		this.studyBoardService = studyBoardService;
		this.recruitService = recruitService;
		this.reviewBoardService = reviewBoardService;

	}

	// 메인 화면 컨트롤러 입니다.
	@GetMapping("/")
	public String home(Model model) throws InterruptedException {

		// 인기 이력서 게시글을 가져옵니다. 이미지를 띄워줍니다.
		List<Map<String , Object>> reviewBoardList = reviewBoardService.getReviewBoardByViewCount();
		
		// 3개씩 잘라서 chunkList 만들기
				List<List<Map<String, Object>>> reviewChunks = new ArrayList<>();
				int chunkSize = 3;
				for (int i = 0; i < reviewBoardList.size(); i += chunkSize) {
					int end = Math.min(i + chunkSize, reviewBoardList.size());
					reviewChunks.add(reviewBoardList.subList(i, end));
				}
		model.addAttribute("reviewChunks", reviewChunks);
		

		// 인기 스터디 게시글을 가져옵니다.
		// 조회 수를 기준으로 가져옵니다.
		// 5개만 가져옵니다.
		List<Map<String, Object>> studyList = studyBoardService.getStudyBoardByViewCount();

		model.addAttribute("Board", studyList);

		// 오늘의 채용 공고를 가져옵니다.
		// 메인 화면을 로딩할 때 데이터가 없다면 크롤링을 시작합니다.
		// 데이터가 있다면 db에서 데이터를 가져옵니다.
		List<Map<String, String>> recruitList = recruitService.getRecruit(1,4);
		if (recruitList.isEmpty()) {
			recruitService.createRecruit(10);
			recruitList = recruitService.getRecruit(1,4);

		}

		model.addAttribute("recruitList", recruitList);

		return "/pages/home/home";

	}

	@GetMapping("/not-logined")
	public String notLogined(@RequestParam(value = "msg") String msg, Model model) {
		if ("login_required".equals(msg)) {
			model.addAttribute("alertMessage", "로그인이 필요한 작업입니다.");
		}
		return "not-logined";
	}
}
