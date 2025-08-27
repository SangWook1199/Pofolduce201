package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.domain.Verification;

@Mapper
public interface AdminMapper {

	List<User> getAllUsers();

	User getUserById(int userId);

	// 제재 기간 및 상태 설정
	void updateSanction(@Param("userId") int userId, @Param("sanctionPeriod") int sanctionPeriod,
			@Param("sanctionType") String sanctionType);

	// 제재 횟수 증가
	void increaseSanctionCount(@Param("userId") int userId);

	// 회사 인증 리스트 조회
	List<Verification> getAllVerifications();

	// 회사 인증 상태 업데이트
	void updateVerificationStatus(@Param("userId") int userId, @Param("state") String state);

	// 유저 회사 인증 정보 업데이트
	void updateUserCompanyInfo(@Param("userId") int userId, @Param("companyCertification") String companyCertification,
			@Param("companyName") String companyName);

	// 특정 유저의 Verification 조회
	Verification getVerificationByUserId(int userId);

}
