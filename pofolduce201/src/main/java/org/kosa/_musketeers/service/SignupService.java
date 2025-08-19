package org.kosa._musketeers.service;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

	@Autowired
	private UserMapper userMapper; // MyBatis Mapper

	// 회원 가입 처리 메서드
	@Transactional
	public boolean registerUser(String email, String nickname, String password, String confirmPassword, String userType,
			String companyName) {
		if (isEmailExists(email)) {
			return false;
		}

		if (!password.equals(confirmPassword)) {
			return false;
		}

		User newUser = new User(email, nickname, password, userType, null, companyName, 0, 0, null, 0, null, null);

		int result = userMapper.insertUser(newUser);
		return result > 0;
	}

	private boolean isEmailExists(String email) {
		return userMapper.findUserByEmail(email) != null;
	}
}
