package org.kosa._musketeers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 채용공고 컨트롤러 입니다.
 */

@Controller
public class RecruitController {
	
	@GetMapping("/recruit")
	public String recruit() {
		return "/pages/recruit/recruit";
	}

}
