package org.kosa._musketeers.controller;

import java.util.List;
import java.util.Map;

import org.kosa._musketeers.domain.StudyBoard;
import org.kosa._musketeers.service.StudyBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudyBoardController {
	private final StudyBoardService studyBoardService;
	
	
	public StudyBoardController(StudyBoardService studyBoardService) {
		super();
		this.studyBoardService = studyBoardService;
	}

	@GetMapping("/")
	public String studyBoard(@RequestParam(defaultValue = "1") int page, Model model) {

	    // 상위 3개 조회수
	    Map<String, StudyBoard> top3Map = studyBoardService.getTop3ByViewCount();
	    model.addAttribute("first", top3Map.get("first"));
	    model.addAttribute("second", top3Map.get("second"));
	    model.addAttribute("third", top3Map.get("third"));

	    // 페이지네이션 적용 (17개)
	    int pageSize = 17;
	    List<StudyBoard> posts = studyBoardService.getPostsByPage(page, pageSize);
	    model.addAttribute("dateList", posts);

	    // 페이지 번호 계산
	    int totalPosts = studyBoardService.countPosts();
	    int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

	    // 5페이지 단위로 나눠서 표시
	    int startPage = ((page - 1) / 5) * 5 + 1;
	    int endPage = Math.min(startPage + 4, totalPages);

	    model.addAttribute("currentPage", page);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    model.addAttribute("totalPages", totalPages);

	    return "pages/study/study-board";
	}
	
	@GetMapping("/study/{studyId}")
	public String studyBoardPost(@PathVariable int studyId, Model model) {
	    StudyBoard post = studyBoardService.getPostById(studyId);
	    model.addAttribute("post", post);
	    return "pages/study/study-board-post"; // 상세 페이지 HTML
	}
	

}
