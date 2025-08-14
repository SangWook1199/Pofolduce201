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
}
