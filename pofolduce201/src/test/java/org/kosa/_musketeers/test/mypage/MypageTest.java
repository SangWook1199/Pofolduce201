package org.kosa._musketeers.test.mypage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MypageTest {
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void deleteAccount() {
		//given
		int userId =  1;
		//when
		
		userMapper.deleteAccount(userId);
		
		//then

	    assertNull(userMapper.getUserInformation(userId)); // 삭제 후 조회하면 null이어야 함
		
		
	}
	
	@Test
	public void updateUserInformation() {
		//given
		int userId =  1;
		String nickname = "nickname2132";
		String email = "user13232@example.com";
		//when
		
		userMapper.updateUserInformation(userId, nickname, email);
		
		//then
		User updatedUser = userMapper.getUserInformation(userId);
		
		assertEquals(nickname, updatedUser.getNickname());
		assertEquals(email, updatedUser.getEmail());

		
	}
}
