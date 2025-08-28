package org.kosa._musketeers.scheduler;

import java.time.LocalDateTime;

import org.kosa._musketeers.mapper.RecruitMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecruitScheduler {
	private final RecruitMapper recruitMapper;

	public RecruitScheduler(RecruitMapper recruitMapper) {
		this.recruitMapper = recruitMapper;
	}

// 5분마다 실행 (300000ms) 
	@Scheduled(fixedRate = 36000000)
	public void clearRecruitData() {
		recruitMapper.deleteRecruit();
		//System.out.println("채용 공고 DB 초기화." + LocalDateTime.now());
	}
}