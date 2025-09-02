package org.kosa._musketeers.controller;

import java.util.Collections;
import java.util.List;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RankingController {
	private final UserService userService;

	public RankingController(UserService userService) {
		this.userService = userService;
	}
	// 순위표 페이지
	@GetMapping("/ranking")
	public String ranking(HttpServletRequest request, Model model) {
		List<User> rankingList;
        try {
            rankingList = userService.getUserListByPoint();

            if (rankingList == null) {
                rankingList = Collections.emptyList();
            }
        } catch (Exception e) {
            rankingList = Collections.emptyList();
            model.addAttribute("errorMessage", "랭킹 데이터를 불러오는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

        model.addAttribute("rankingList", rankingList);
        model.addAttribute("currentUri", request.getRequestURI());
		return "pages/ranking/ranking";
	}
}
