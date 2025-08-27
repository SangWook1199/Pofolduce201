package org.kosa._musketeers.service;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

    @Autowired
    private UserMapper userMapper; // MyBatis Mapper

    // 회원가입 전용으로 직접 생성
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public String registerUser(String email, String nickname, String password, String confirmPassword, String userType,
                               String companyName) {

        // 이메일 중복 체크
        if (isEmailExists(email)) {
            return "EMAIL_DUPLICATE"; // 이메일 중복
        }

        // 닉네임 중복 체크
        if (isNicknameExists(nickname)) {
            return "NICKNAME_DUPLICATE"; // 닉네임 중복
        }

        // 비밀번호 확인
        if (!password.equals(confirmPassword)) {
            return "PASSWORD_MISMATCH"; // 비밀번호 불일치
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // User 객체 필요한 필드만 세팅
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setNickname(nickname);
        newUser.setPassword(encodedPassword);
        newUser.setUserType(userType);
        newUser.setCompanyName(companyName); // 회사 인증은 나중에 별도 처리

        int result = userMapper.insertUser(newUser);
        return result > 0 ? "SUCCESS" : "FAIL"; // DB 삽입 성공 여부
    }

    private boolean isEmailExists(String email) {
        return userMapper.findUserByEmail(email) != null;
    }

    private boolean isNicknameExists(String nickname) {
        return userMapper.findUserByNickname(nickname) != null;
    }
}
