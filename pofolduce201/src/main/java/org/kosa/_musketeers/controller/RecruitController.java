package org.kosa._musketeers.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kosa._musketeers.service.CrawlingService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * 채용공고 컨트롤러 입니다.
 */

@Controller
public class RecruitController {
	
	private final CrawlingService crawlingService;
	
	public RecruitController(CrawlingService crawlingService) {
		super();
		this.crawlingService = crawlingService;
	}
	
	
	//채용 공고 홈 컨트롤러 입니다.
	//db에 저장된 공고 데이터를 불러옵니다.
	@GetMapping("/recruit")
	public String recruit() {
		
		
		
		
		
		return "/pages/recruit/recruit";
	}
	
	//채용 공고를 크롤링 하는 메소드 입니다.
	//공고를 가져온 후 DB에 저장합니다.
	@GetMapping("/recruit/crawling")
	public void getRecruit() throws InterruptedException {
		// 드라이버 세팅
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		// 가져올 사이트의 링크를 넣기
		driver.get("https://www.jobplanet.co.kr/job");
		
		// select 요소 찾기 (ID 기준)
		WebElement selectElement = driver.findElement(By.id("location"));

		// Select 객체 생성
		Select select = new Select(selectElement);

		// 최신순 선택
		select.selectByValue("recent"); // value 속성 기준
		Thread.sleep(1000); // 페이지 로딩 대기

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// 중복 제거를 위해 Set 사용
		Set<String> collectedLinks = new HashSet<>();

		int scrollCount = 5; // 스크롤 몇 번 내릴지
	
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
					// "합격보상 100만원" 문자열 제거
					text = text.replace("합격보상 100만원", "");

					String[] lines = text.split("\n");
					System.out.println("제목: " + lines[0]);
					System.out.println("직종: " + lines[1]);
					System.out.println("조건: " + lines[2]);
					System.out.println("링크: " + link);
					System.out.println("이미지: " + imgUrl);
					System.out.println("-----");
					
					crawlingService.createRecruit(lines[0], lines[1], lines[2], link, imgUrl);

					collected++;
				
				}
			}

			// 스크롤 내리기
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			Thread.sleep(1000); // 로딩 대기
		}

		driver.quit();
	}

}
