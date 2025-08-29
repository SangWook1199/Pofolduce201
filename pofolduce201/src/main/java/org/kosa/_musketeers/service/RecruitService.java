package org.kosa._musketeers.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kosa._musketeers.mapper.RecruitMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.bonigarcia.wdm.WebDriverManager;

//웹 크롤링 서비스 입니다. 
@Service
@Transactional
public class RecruitService {

	private RecruitMapper recruitMapper;

	public RecruitService(RecruitMapper recruitMapper) {
		super();
		this.recruitMapper = recruitMapper;
	}

	// 데이터베이스에 채용 공고를 저장하는 메소드 입니다.
	// 웹 크롤링 -> db 저장
	public void createRecruit(int count) throws InterruptedException {
		// 드라이버 세팅
		WebDriverManager.chromedriver().setup();

		// 크롬 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // 브라우저 창 안 띄움
		options.addArguments("--disable-gpu"); // GPU 사용 안함 (윈도우 필수)
		options.addArguments("--no-sandbox"); // 샌드박스 모드 비활성 (리눅스 환경에서 종종 필요)
		options.addArguments("--disable-dev-shm-usage"); // 공유 메모리 사용 안함 (Docker 환경에서 필요)
		options.addArguments("--window-size=1920,1080"); // 가상 창 크기

		// User-Agent 설정 (크롤링 차단 우회용)
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
				+ "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/114.0.0.0 Safari/537.36");
		// 수정
		WebDriver driver = new ChromeDriver(options);

		// 가져올 사이트의 링크를 넣기
		driver.get("https://www.jobplanet.co.kr/job");

		// select 요소 찾기 (ID 기준)
		WebElement selectElement = driver.findElement(By.id("location"));

		// Select 객체 생성
		Select select = new Select(selectElement);

		// 최신순 선택
		select.selectByValue("recent"); // value 속성 기준
		Thread.sleep(700); // 페이지 로딩 대기

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// 중복 제거를 위해 Set 사용
		Set<String> collectedLinks = new HashSet<>();

		int scrollCount = count; // 스크롤 몇 번 내릴지
		for (int i = 0; i < scrollCount; i++) {
			// 현재 페이지에서 공고 가져오기
			List<WebElement> jobList = driver.findElements(By.xpath("//a[contains(@href, '/job/')]"));
			
			int collected = 0;
			for (WebElement job : jobList) {
				WebElement imgElement = job.findElement(By.tagName("img"));
				String link = job.getAttribute("href");
				String imgUrl = imgElement.getAttribute("src");

				// 중복된 건 건너뜀
				if (!collectedLinks.contains(link)) {
					collectedLinks.add(link);
					String text = job.getText();
					// "합격보상 100만원" 들어간 줄 통째로 제거
					text = text.replaceAll(".*합격보상 100만원.*(\\r?\\n)?", "");

					String[] lines = text.split("\n");

					// 안전하게 꺼내기
					String company = (lines.length > 0) ? lines[0] : "";
					String job1 = (lines.length > 1) ? lines[1] : "";
					String cond = (lines.length > 2) ? lines[2] : "";


					// DB 저장도 안전 변수 사용
					recruitMapper.saveRecruit(company, job1, cond, link, imgUrl);

					collected++;

				}
			}

			// 스크롤 내리기
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			Thread.sleep(1000); // 로딩 대기
		}
		
		driver.quit();

		// 데이터 저장

	}

	// 공고를 가져오는 메소드 입니다.
	public List<Map<String, String>> getRecruit(int page, int size) {

		List<Map<String, String>> recruitList = recruitMapper.getRecruit();

		// 2. company가 null 이거나 빈 문자열("")인 항목 제거
		recruitList.removeIf(r -> r.get("company").trim().isEmpty()); 	
		
		int totalCount = recruitList.size();

		// 페이징 (subList 사용)
		int fromIndex = Math.max(0, (page - 1) * size);
		int toIndex = Math.min(fromIndex + size, totalCount);

		if (fromIndex > toIndex) {
			return new ArrayList<>(); // 빈 리스트
		}

		return recruitList.subList(fromIndex, toIndex);

	}

	public int countRecruit() {
		int count = recruitMapper.getAllRecruit();
		return count;
	}

}
