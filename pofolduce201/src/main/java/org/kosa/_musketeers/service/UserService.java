package org.kosa._musketeers.service;

import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserMapper userMapper;

	UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User login(String email, String password) {
		return userMapper.getUserIdByEmailPwd(email, password);
	}

	// 마이페이지에서 내 정보를 가져오는 메소드 입니다.
	public User getUserInformation(int userId) {
		
		User result = userMapper.getUserInformation(userId);
		
		return result;
	}
	
	//마이페이지에서 내 정보를 수정하는 메소드 입니다.
	public User updateUserInfomation(int userId) {
		return null;
	}

	// 마이페이지에서 회원 탈퇴를 하는 메소드 입니다.
	public boolean deleteAccount(int userId) {
		
		boolean result = userMapper.deleteAccount(userId);

		return false;
	}

}
