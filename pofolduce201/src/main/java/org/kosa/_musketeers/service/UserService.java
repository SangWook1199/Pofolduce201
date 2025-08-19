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

	// 마이페이지에서 내 정보를 수정하는 메소드 입니다.
	// 1. 닉네임 중복 체크
	// 2. 이메일 중복 체크
	public boolean updateUserInfomation(int userId, String nickname, String email) {

		//Integer 타입으로 변경 (null이 반환될수도 있음)
		Integer nickNameUserId = userMapper.getUserNickNameById(nickname);
		Integer emailUserId = userMapper.getUserEmailById(email);

		// 닉네임이 존재하는 경우 (즉 변경하려는 닉네임이 다른 유저의 닉네임인 경우)
		// nickNameUserId != userId 만 작성하면 오류가 발생한다. (null은 비교 자체가 불가능하기 때문)
		if (nickNameUserId != null && nickNameUserId != userId) {
			
			return false;
			
		} else if (emailUserId != null && emailUserId != userId) {
			//이메일이 존재하는 경우
				return false;
		} else {
			userMapper.updateUserInformation(userId, nickname, email);
			return true;
		}

	}

	// 마이페이지에서 회원 탈퇴를 하는 메소드 입니다.
	public boolean deleteAccount(int userId) {

		boolean result = userMapper.deleteAccount(userId);

		return result;
	}

}
