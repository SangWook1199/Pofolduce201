package org.kosa._musketeers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 검색 조회의 controller 입니다.
 */
@Controller
public class SearchController {
	
	
	//검색 페이지 입니다.
	
	@GetMapping("/search")
	public String search() {
		
		
		return "/pages/search/search";
	}


}
