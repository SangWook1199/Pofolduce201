package org.kosa._musketeers.controller;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	UserService userService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public UserController(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "pages/login/login";
	}
	
	@PostMapping("/login/processing")
	public String loginProcessing(@RequestParam String email, @RequestParam String password, HttpServletRequest request,  RedirectAttributes redirectAttributes) {
		
		logger.info("email:" + email + " password" + password);
		User user = userService.login(email, password);
		String page = "redirect:/login";
		if(user != null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("userId", user.getUserId());
			page = "redirect:/home";
		}else {
			redirectAttributes.addFlashAttribute("loginFailMessage", "아이디 or 비밀번호가 잘못입력되었습니다");
		}
		
		return page;
	}
	
	@GetMapping("/home")
	public String home() {
		return "index";
	}
}
