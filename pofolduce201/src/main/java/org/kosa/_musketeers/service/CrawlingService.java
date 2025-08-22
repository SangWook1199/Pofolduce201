package org.kosa._musketeers.service;

import java.util.List;
import java.util.Map;

import org.kosa._musketeers.mapper.RecruitMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//웹 크롤링 서비스 입니다. 
@Service
@Transactional
public class CrawlingService {

	private RecruitMapper recruitMapper;

	public CrawlingService(RecruitMapper recruitMapper) {
		super();
		this.recruitMapper = recruitMapper;
	}

	// 데이터베이스에 채용 공고를 저장하는 메소드 입니다.
	public void createRecruit(String title, String company, String content, String link, String imgLink) {

		recruitMapper.saveRecruit(title, company, content, link, imgLink);

	}

	// 공고를 가져오는 메소드 입니다.
	public List<Map<String, String>> getRecruit() {
		
		List<Map<String, String>> recruitList = recruitMapper.getRecruit();
		return recruitList;

	}

}
