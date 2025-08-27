package org.kosa._musketeers.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kosa._musketeers.service.RecruitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 채용공고 컨트롤러 입니다.
 */

@Controller
public class RecruitController {

	private final RecruitService recruitService;

	public RecruitController(RecruitService recruitService) {
		super();
		this.recruitService = recruitService;
	}

	// 채용 공고 홈 컨트롤러 입니다.
	// db에 저장된 공고 데이터를 불러옵니다.
	@GetMapping("/recruit")
	public String recruit(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size, HttpServletRequest request) throws InterruptedException {

		// 크롤링한 데이터가 없다면 크롤링을 실행합니다.
		List<Map<String, String>> recruitList = recruitService.getRecruit(page, size);
		int totalCount = recruitService.countRecruit();
		
		if (totalCount == 0) {
			recruitService.createRecruit(10);
			recruitList = recruitService.getRecruit(page, size);

		}

		// 크롤링 후 저장된 db의 데이터를 가져옵니다.
		//recruitList = recruitService.getRecruit(page, size);
		
		int totalPages = (int) Math.ceil((double) totalCount / size);
		// 전달합니다.
		List<Map<String, String>> recruitListToday = recruitService.getRecruit(1, 9);
		// 3개씩 잘라서 chunkList 만들기
		List<List<Map<String, String>>> recruitChunks = new ArrayList<>();
		int chunkSize = 3;
		for (int i = 0; i < recruitListToday.size(); i += chunkSize) {
			int end = Math.min(i + chunkSize, recruitListToday.size());
			recruitChunks.add(recruitListToday.subList(i, end));
		}
		model.addAttribute("recruitChunks", recruitChunks);
		
		model.addAttribute("recruitList", recruitList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);
		model.addAttribute("currentUri", request.getRequestURI());

		return "/pages/recruit/recruit";
	}

}
