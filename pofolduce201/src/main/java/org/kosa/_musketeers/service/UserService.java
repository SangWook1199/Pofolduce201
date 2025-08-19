package org.kosa._musketeers.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
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

		// Integer 타입으로 변경 (null이 반환될수도 있음)
		Integer nickNameUserId = userMapper.getUserNickNameById(nickname);
		Integer emailUserId = userMapper.getUserEmailById(email);

		// 닉네임이 존재하는 경우 (즉 변경하려는 닉네임이 다른 유저의 닉네임인 경우)
		// nickNameUserId != userId 만 작성하면 오류가 발생한다. (null은 비교 자체가 불가능하기 때문)
		if (nickNameUserId != null && nickNameUserId != userId) {

			return false;

		} else if (emailUserId != null && emailUserId != userId) {
			// 이메일이 존재하는 경우
			return false;
		}
		if (nickNameUserId != userId) {

			return false;
		}
		if (nickNameUserId != userId) {

			return false;

		} else if (emailUserId != userId) {
			// 이메일이 존재하는 경우
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

	private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
		int targetHeight = (int) ((double) originalImage.getHeight() / originalImage.getWidth() * targetWidth);
		Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(scaledImage, 0, 0, null);
		g2d.dispose();
		return resizedImage;
	}

	public Portfolio createPortfolio(MultipartFile file, String portfolioName, User user) throws IOException {
		// 1. DB에 portfolio 레코드 먼저 생성 (파일 경로는 아직 비워둠)
		Portfolio portfolio = new Portfolio(portfolioName, user);
		portfolioMapper.createPortfolio(portfolio);

		// 2. 업로드 디렉터리 경로 설정
		Path uploadDir = Paths.get("src/main/resources/static/uploads/portfolio/");
		Files.createDirectories(uploadDir);

		// 3. PDF 저장 (portfolioId.pdf)
		Path pdfPath = uploadDir.resolve(portfolio.getPortfolioId() + ".pdf");
		Files.copy(file.getInputStream(), pdfPath, StandardCopyOption.REPLACE_EXISTING);

		// 4. PDF → PNG 변환 (첫 페이지)
		try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage image = pdfRenderer.renderImageWithDPI(0, 150); // 첫 페이지, 150dpi

			// 리사이즈 (예: 가로 400px)
			BufferedImage resizedImage = resizeImage(image, 180);

			// PNG 저장
			Path imgPath = uploadDir.resolve(portfolio.getPortfolioId() + ".png");
			ImageIO.write(resizedImage, "PNG", imgPath.toFile());
		}

		return portfolio;
	}

	public List<Portfolio> getPortfolioList(int userId) {
		return portfolioMapper.getPortfolioList(userId);
	}

}
