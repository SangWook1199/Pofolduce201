package org.kosa._musketeers.controller;

import java.util.List;
import java.util.Map;

import org.kosa._musketeers.service.StudyBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//메인 화면을 다루는 컨트롤러 입니다.
public class HomeController {

	// 스터디 게시글을 조회하는 서비스를 가져옵니다.
	StudyBoardService studyBoardService;

	public HomeController(StudyBoardService studyBoardService) {
		this.studyBoardService = studyBoardService;

	}

	// 메인 화면 컨트롤러 입니다.
	@GetMapping("/")
	public String home(Model model) {

		// 인기 이력서 게시글을 가져옵니다. 이미지를 띄워줍니다.

		// 인기 스터디 게시글을 가져옵니다.
		// 조회 수를 기준으로 가져옵니다.
		// 5개만 가져옵니다.
		List<Map<String, Object>> list = studyBoardService.getStudyBoardByViewCount();

		model.addAttribute("Board", list);

		return "/pages/home/home";

	}

	@GetMapping("/not-logined")
	public String notLogined(@RequestParam(value = "msg")String msg, Model model) {
		if ("login_required".equals(msg)) {
            model.addAttribute("alertMessage", "로그인이 필요한 작업입니다.");
        }
		return "not-logined";
	}
}
