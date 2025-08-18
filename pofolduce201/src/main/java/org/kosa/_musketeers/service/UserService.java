package org.kosa._musketeers.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.kosa._musketeers.domain.Portfolio;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.PortfolioMapper;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

	private UserMapper userMapper;
	private PortfolioMapper portfolioMapper;

	public UserService(UserMapper userMapper, PortfolioMapper portfolioMapper) {
		super();
		this.userMapper = userMapper;
		this.portfolioMapper = portfolioMapper;
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

		int nickNameUserId = userMapper.getUserNickNameById(nickname);
		int emailUserId = userMapper.getUserNickNameById(email);

		// 닉네임이 존재하는 경우 (즉 변경하려는 닉네임이 다른 유저의 닉네임인 경우)
		if (nickNameUserId != userId) {
			
			return false;
			
		} else if (emailUserId != userId) {
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

		return false;
	}

	public Portfolio createPortfolio(MultipartFile file, String portfolioName, User user) throws IOException {
		// 1. DB에 portfolio 레코드 먼저 생성 (파일 경로는 아직 비워둠)
        Portfolio portfolio = new Portfolio(portfolioName, user);
        portfolioMapper.createPortfolio(portfolio);

        // 2. 파일 저장 (portfolioId.pdf)
        Path uploadPath = Paths.get("src/main/resources/static/uploads/" + portfolio.getPortfolioId() + ".pdf");
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

        return portfolio;
	}

}
