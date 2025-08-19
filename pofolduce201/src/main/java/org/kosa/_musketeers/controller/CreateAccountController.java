package org.kosa._musketeers.controller;

import org.kosa._musketeers.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CreateAccountController {

    @Autowired
    private SignupService signupService;

    // 회원가입 폼 보여주기
    @GetMapping("/signup")
    public String showSignupForm() {
        return "pages/signup/createaccount";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@RequestParam String email,
                         @RequestParam String nickname,
                         @RequestParam String password,
                         @RequestParam String confirmPassword,
                         Model model) {

        boolean isSuccess = signupService.registerUser(email, nickname, password, confirmPassword, null, null);

        if (isSuccess) {
            return "redirect:/login";
        } else {
            model.addAttribute("errorMessage", "이미 존재하는 이메일이거나 비밀번호가 일치하지 않습니다.");
            return "pages/signup/createaccount";
        }
    }
}
