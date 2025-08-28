package org.kosa._musketeers.runner;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateAdmin implements CommandLineRunner {
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public CreateAdmin(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

	@Override
	public void run(String... args) throws Exception {

		// 이미 admin 계정이 있는지 확인
		User existingAdmin = userMapper.findUserByEmail("admin@example.com");

		if (existingAdmin == null) {
			// 비밀번호 암호화
			String password = "pass1234";
			String encodedPassword = passwordEncoder.encode(password);
			User admin = new User();
			admin.setEmail("admin@example.com");
			admin.setNickname("관리자01");
			admin.setPassword(encodedPassword);
			admin.setUserType("관리자");
			admin.setCompanyName("미인증"); // 회사 인증은 나중에 별도 처리

			userMapper.insertAdmin(admin);
		} else {
			// 관리자 계정 생성하지 않음
		}
	}

}
