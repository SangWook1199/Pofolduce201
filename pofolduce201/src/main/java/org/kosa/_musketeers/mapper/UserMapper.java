package org.kosa._musketeers.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kosa._musketeers.domain.User;

@Mapper
public interface UserMapper {

	User getUserIdByEmailPwd(@Param("email") String email, @Param("password") String password);
}
