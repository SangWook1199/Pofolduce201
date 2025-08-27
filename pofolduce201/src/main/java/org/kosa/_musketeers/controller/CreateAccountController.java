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
                         @RequestParam(required = false) String userType,
                         @RequestParam(required = false) String companyName,
                         Model model) {

        // 서비스에서 회원가입 시도
        String result = signupService.registerUser(email, nickname, password, confirmPassword, userType, companyName);

        // 결과에 따라 뷰 처리
        switch (result) {
            case "SUCCESS":
                return "redirect:/login"; // 가입 성공 → 로그인 페이지로
            case "EMAIL_DUPLICATE":
                model.addAttribute("errorMessage", "이미 사용 중인 이메일입니다.");
                break;
            case "NICKNAME_DUPLICATE":
                model.addAttribute("errorMessage", "이미 사용 중인 닉네임입니다.");
                break;
            case "PASSWORD_MISMATCH":
                model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                break;
            default:
                model.addAttribute("errorMessage", "회원가입에 실패했습니다. 다시 시도해주세요.");
                break;
        }

        return "pages/signup/createaccount";
    }
}
