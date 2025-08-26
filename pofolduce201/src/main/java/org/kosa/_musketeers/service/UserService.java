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

	// 포트폴리오 리스트를 불러오는 메서드입니다.
	public List<Portfolio> getPortfolioList(int userId) {
		return portfolioMapper.getPortfolioList(userId);
	}

	// 특정 포트폴리오를 불러오는 메서드입니다.
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

	// 대표 포트폴리오를 설정하는 메서드입니다.
	public void setRepPortfolio(int userId, int portfolioId) {
		// 새 대표 포트폴리오 추가
		portfolioMapper.updateRepPortfolio(userId, portfolioId);
	}

	// 처음 포트폴리오를 대표 포트폴리오를 설정하는 메서드입니다.
	public void setFirstRepPortfolio(int userId, int portfolioId) {
		portfolioMapper.setRepPortfolio(userId, portfolioId);
	}

	// 대표 포트폴리오가 있는지 확인하는 메서드입니다.
	public Integer getRepPortfolio(int userId) {
		return portfolioMapper.getRepPortfolio(userId);
	}

	// 아이디로 포트폴리오 아이디를 구하는 메서드입니다.
	public int getPortfolioById(int userId) {
		return portfolioMapper.getPortfolioById(userId);
	}

	// 포트폴리오를 삭제하는 메서드입니다.
	public void deletePortfolio(int portfolioId) {
		// 1) 파일 경로 생성
		Path uploadDir = Paths.get("src/main/resources/static/uploads/portfolio/");
		Path pdfPath = uploadDir.resolve(portfolioId + ".pdf");
		Path pngPath = uploadDir.resolve(portfolioId + ".png");

		// 2) 파일 삭제 (존재하면)
		try {
			if (Files.exists(pdfPath)) {
				Files.delete(pdfPath);
			}
			if (Files.exists(pngPath)) {
				Files.delete(pngPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
			// 로그 남기거나 예외 처리 가능
		}
		// 3) DB에서 삭제
		portfolioMapper.deletePortfolio(portfolioId);
	}

	// 유저의 프로필을 등록하는 메소드 입니다.
	public void updateProfile(MultipartFile file, int userId) throws IOException {

		// 1. 업로드 디렉터리 경로 설정
		Path uploadDir = Paths.get("src/main/resources/static/uploads/profile/");
		Files.createDirectories(uploadDir);

		// 2. 업로드하기
		Path imagePath = uploadDir.resolve(userId + ".png");
		Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
		String imagePathStr = imagePath.toString();
		// 3. db에 경로 저장하기
		userMapper.updateProfile(userId, imagePathStr);

	}

	public int getTotalPortfolioCountById(int userId) {
		return userMapper.getTotalPortfolioCountById(userId);
	}

	// 검색어를 입력하면 검색을 할 수 있는 메소드 입니다.
	// 첨삭 게시판 검색 결과 -> list로 반환
	public List<Map<String, Object>> getReviewSearchResult(String search, int page, int size) {

		List<Map<String, Object>> list = userMapper.getReviewSearchResult(search);
		int totalCount = list.size();
		
		// 페이징 (subList 사용)
	    int fromIndex = Math.max(0, (page - 1) * size);
	    int toIndex = Math.min(fromIndex + size, totalCount);

	    if (fromIndex > toIndex) {
	        return new ArrayList<>(); // 빈 리스트
	    }

		return list.subList(fromIndex, toIndex);

	}

	// 검색어를 입력하면 검색을 할 수 있는 메소드 입니다.
	// 스터디 게시판 검색 결과 -> list 로 반환
	public List<Map<String, Object>> getStudySearchResult(String search, int page, int size) {

		List<Map<String, Object>> list = userMapper.getStudySearchResult(search);
		int totalCount = list.size();
		
		// 페이징 (subList 사용)
	    int fromIndex = Math.max(0, (page - 1) * size);
	    int toIndex = Math.min(fromIndex + size, totalCount);

	    if (fromIndex > toIndex) {
	        return new ArrayList<>(); // 빈 리스트
	    }

		return list.subList(fromIndex, toIndex);
	}

	public int countReviewResult(String search) {
		int reviewCount = userMapper.getReviewSearchResult(search).size();

		return reviewCount;
	}

	public int countStudyResult(String search) {
		int studyCount = userMapper.getStudySearchResult(search).size();
		return studyCount;
	}

	//채용 공고 검색 결과 서비스 입니다.
	public List<Map<String, Object>> getRecruitSearchResult(String search, int page, int size) {
		List<Map<String, Object>> list = userMapper.getRecruitSearchResult(search);
		int totalCount = list.size();
		
		// 페이징 (subList 사용)
	    int fromIndex = Math.max(0, (page - 1) * size);
	    int toIndex = Math.min(fromIndex + size, totalCount);

	    if (fromIndex > toIndex) {
	        return new ArrayList<>(); // 빈 리스트
	    }

		return list.subList(fromIndex, toIndex);
	}

	public int countRecruitResult(String search) {
		int recruitCount = userMapper.getRecruitSearchResult(search).size();

		return recruitCount;
	}

	//회사 인증서비스 입니다.
	public void insertCompany(MultipartFile file, int userId, String company) throws IOException {
		
		// 1. 업로드 디렉터리 경로 설정
				Path uploadDir = Paths.get("src/main/resources/static/uploads/company/");
				Files.createDirectories(uploadDir);

				// 2. 업로드하기
				Path imagePath = uploadDir.resolve(userId + ".png");
				Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
				String imagePathStr = imagePath.toString();
				// 3. db에 경로 저장하기
				userMapper.insertCompany(userId, imagePathStr, company);
		
	}
	
	//유저의 회사 인증 정보를 가져옵니다.
	public Verification getUserCompanyVerification(int userId) {
		
		Verification userCompanyData = userMapper.getUserCompanyDataById(userId);
		
		
		return userCompanyData;
	}
	

	
}
