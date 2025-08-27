package org.kosa._musketeers.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.kosa._musketeers.domain.Portfolio;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.domain.Verification;
import org.kosa._musketeers.mapper.PortfolioMapper;
import org.kosa._musketeers.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
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

	public User getUserInformation(int userId) {
		return userMapper.getUserInformation(userId);
	}

	// 메서드명 오타 수정: updateUserInfomation -> updateUserInformation
	public boolean updateUserInformation(int userId, String nickname, String email) {

		Integer nickNameUserId = userMapper.getUserNickNameById(nickname);
		Integer emailUserId = userMapper.getUserEmailById(email);

		if (nickNameUserId != null && !nickNameUserId.equals(userId)) {
			return false;
		} else if (emailUserId != null && !emailUserId.equals(userId)) {
			return false;
		} else {
			userMapper.updateUserInformation(userId, nickname, email);
			return true;
		}
	}

	public boolean deleteAccount(int userId) {
		int rows = userMapper.deleteAccount(userId);
		return rows > 0;
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
		Portfolio portfolio = new Portfolio(portfolioName, user);
		portfolioMapper.createPortfolio(portfolio);

		Path uploadDir = Paths.get("src/main/resources/static/uploads/portfolio/");
		Files.createDirectories(uploadDir);

		Path pdfPath = uploadDir.resolve(portfolio.getPortfolioId() + ".pdf");
		Files.copy(file.getInputStream(), pdfPath, StandardCopyOption.REPLACE_EXISTING);

		try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage image = pdfRenderer.renderImageWithDPI(0, 150);

			BufferedImage resizedImage = resizeImage(image, 180);

			Path imgPath = uploadDir.resolve(portfolio.getPortfolioId() + ".png");
			ImageIO.write(resizedImage, "PNG", imgPath.toFile());
		}

		return portfolio;
	}

	public List<Portfolio> getPortfolioList(int userId) {
		return portfolioMapper.getPortfolioList(userId);
	}

	public Portfolio getPortfolio(int portfolioId) {
		return portfolioMapper.getPortfolio(portfolioId);
	}

	public User getUserById(int userId) {
		return userMapper.getUserById(userId);
	}

	public boolean isAdmin(int userId) {
		User user = getUserById(userId);
		return user != null && "관리자".equals(user.getUserType());
	}

	public List<User> getAllUsers() {
		return userMapper.getAllUsers();
	}

	public void setRepPortfolio(int userId, int portfolioId) {
		portfolioMapper.updateRepPortfolio(userId, portfolioId);
	}

	public void setFirstRepPortfolio(int userId, int portfolioId) {
		portfolioMapper.setRepPortfolio(userId, portfolioId);
	}

	public Integer getRepPortfolio(int userId) {
		return portfolioMapper.getRepPortfolio(userId);
	}

	public int getPortfolioById(int userId) {
		return portfolioMapper.getPortfolioById(userId);
	}

	public void deletePortfolio(int portfolioId) {
		Path uploadDir = Paths.get("src/main/resources/static/uploads/portfolio/");
		Path pdfPath = uploadDir.resolve(portfolioId + ".pdf");
		Path pngPath = uploadDir.resolve(portfolioId + ".png");

		try {
			if (Files.exists(pdfPath)) {
				Files.delete(pdfPath);
			}
			if (Files.exists(pngPath)) {
				Files.delete(pngPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		portfolioMapper.deletePortfolio(portfolioId);
	}

	public void updateProfile(MultipartFile file, int userId) throws IOException {
		Path uploadDir = Paths.get("src/main/resources/static/uploads/profile/");
		Files.createDirectories(uploadDir);

		Path imagePath = uploadDir.resolve(userId + ".png");
		Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
		String imagePathStr = imagePath.toString();

		userMapper.updateProfile(userId, imagePathStr);
	}

	public int getTotalPortfolioCountById(int userId) {
		return userMapper.getTotalPortfolioCountById(userId);
	}

	public List<Map<String, Object>> getReviewSearchResult(String search, int page, int size) {
		List<Map<String, Object>> list = userMapper.getReviewSearchResult(search);
		int totalCount = list.size();

		int fromIndex = Math.max(0, (page - 1) * size);
		int toIndex = Math.min(fromIndex + size, totalCount);

		if (fromIndex > toIndex) {
			return new ArrayList<>();
		}

		return list.subList(fromIndex, toIndex);
	}

	public List<Map<String, Object>> getStudySearchResult(String search, int page, int size) {
		List<Map<String, Object>> list = userMapper.getStudySearchResult(search);
		int totalCount = list.size();

		int fromIndex = Math.max(0, (page - 1) * size);
		int toIndex = Math.min(fromIndex + size, totalCount);

		if (fromIndex > toIndex) {
			return new ArrayList<>();
		}

		return list.subList(fromIndex, toIndex);
	}

	public int countReviewResult(String search) {
		return userMapper.getReviewSearchResult(search).size();
	}

	public int countStudyResult(String search) {
		return userMapper.getStudySearchResult(search).size();
	}

	public List<Map<String, Object>> getRecruitSearchResult(String search, int page, int size) {
		List<Map<String, Object>> list = userMapper.getRecruitSearchResult(search);
		int totalCount = list.size();

		int fromIndex = Math.max(0, (page - 1) * size);
		int toIndex = Math.min(fromIndex + size, totalCount);

		if (fromIndex > toIndex) {
			return new ArrayList<>();
		}

		return list.subList(fromIndex, toIndex);
	}

	public int countRecruitResult(String search) {
		return userMapper.getRecruitSearchResult(search).size();
	}

	public void insertCompany(MultipartFile file, int userId, String company) throws IOException {
		Path uploadDir = Paths.get("src/main/resources/static/uploads/company/");
		Files.createDirectories(uploadDir);

		Path imagePath = uploadDir.resolve(userId + ".png");
		Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
		String imagePathStr = imagePath.toString();

		userMapper.insertCompany(userId, imagePathStr, company);
	}

	public List<User> getUserListByPoint() {
		return userMapper.getUserListByPoint();
	}

	public Verification getUserCompanyVerification(int userId) {
		return userMapper.getUserCompanyDataById(userId);
	}

	public List<User> getUsersRequestingCertification() {
		return userMapper.getUsersRequestingCertification();
	}

	/**
	 * 유저의 회사 인증 상태와 회사 이름을 업데이트합니다.
	 * @param userId               업데이트할 유저 ID
	 * @param companyCertification 업데이트할 인증 상태 ('yes' 또는 'no')
	 * @param companyName          업데이트할 회사 이름 (인증 안함일 경우 null)
	 */
	@Transactional
	public void updateUserCompanyInfo(int userId, String companyCertification, String companyName) {
		userMapper.updateUserCompanyInfo(userId, companyCertification, companyName);
	}
}