package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kosa._musketeers.domain.User;

@Mapper
public interface AdminMapper {

	List<User> getAllUsers();

	User getUserById(int userId);

	// 제재 기간 설정
	void updateSanction(@Param("userId") int userId, @Param("sanctionPeriod") int sanctionPeriod, @Param("sanctionType") String sanctionType);


	// 제재 횟수 증가 (Map 사용)
	void increaseSanctionCount(@Param("userId") int userId);
}
