package org.kosa._musketeers.controller;

import java.util.List;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RankingController {
	private final UserService userService;

	public RankingController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/ranking")
	public String ranking(Model model) {
		List<User> rankingList = userService.getUserListByPoint();
		System.out.println(rankingList);
		model.addAttribute("rankingList", rankingList);
		return "pages/ranking/ranking";
	}
}
