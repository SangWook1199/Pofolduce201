package org.kosa._musketeers.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.domain.Verification;

@Mapper
public interface UserMapper {

	User getUserIdByEmailPwd(@Param("email") String email, @Param("password") String password);

	User getUserInformation(int userId);

	int deleteAccount(int userId);

	void updateUserInformation(int userId, String nickname, String email);

	Integer getUserNickNameById(String nickname);

	Integer getUserEmailById(String email);

	int insertUser(User newUser);

	User findUserByEmail(String email);

	User findUserByNickname(String nickname);

	void updateProfile(int userId, String imagePath);

	int getTotalPortfolioCountById(int userId);

	// user 정보 (관리자 확인용)
	User getUserById(int userId);

	// 검색
	List<Map<String, Object>> getReviewSearchResult(@Param("search") String search);

	List<Map<String, Object>> getStudySearchResult(@Param("search") String search);

	List<Map<String, Object>> getRecruitSearchResult(String search);

	// 회사 인증 정보
	void insertCompany(int userId, String imagePath, String company);

	Verification getUserCompanyDataById(int userId);

	// User 정보(전체 유저 목록 추출)
	List<User> getAllUsers();

	// 관리자 제재 기능 추가
	void updateSanction(@Param("userId") int userId, @Param("days") int days, @Param("status") String status);

	void increaseSanctionCount(@Param("userId") int userId);

	// 회사 인증 요청
	List<User> getUsersRequestingCertification();

	List<User> getUserListByPoint();

	// 새로 추가된 메소드
	void updateUserCompanyInfo(@Param("userId") int userId, @Param("companyCertification") String companyCertification,
			@Param("companyName") String companyName);

}