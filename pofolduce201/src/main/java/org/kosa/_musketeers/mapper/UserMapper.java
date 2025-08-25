package org.kosa._musketeers.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kosa._musketeers.domain.User;

@Mapper
public interface UserMapper {

	User getUserIdByEmailPwd(@Param("email") String email, @Param("password") String password);

	User getUserInformation(int userId);

	boolean deleteAccount(int userId);

	void updateUserInformation(int userId, String nickname, String email);

	Integer getUserNickNameById(String nickname);

	Integer getUserEmailById(String email);

	int insertUser(User newUser);

	User findUserByEmail(String email);



	void updateProfile(int userId, String imagePath);


	int getTotalPortfolioCountById(int userId);

	
	// user 정보 (관리자 확인용)
	User getUserById(int userId);

	
	//검색
	List<Map<String, Object>> getReviewSearchResult(@Param("search") String search);

	List<Map<String, Object>> getStudySearchResult(@Param("search") String search);

	List<Map<String, Object>> getRecruitSearchResult(String search);

}
