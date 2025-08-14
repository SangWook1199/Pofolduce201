package org.kosa._musketeers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kosa._musketeers.domain.StudyBoard;
import org.kosa._musketeers.mapper.StudyBoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudyBoardService {
	private final StudyBoardMapper studyBoardMapper;

	public StudyBoardService(StudyBoardMapper studyBoardMapper) {
		super();
		this.studyBoardMapper = studyBoardMapper;
	}

	public Map<String, StudyBoard> getTop3ByViewCount() {
		List<StudyBoard> list = studyBoardMapper.getPostOrderByViewCount();
		Map<String, StudyBoard> top3 = new HashMap<>();
		if (!list.isEmpty()) {
			top3.put("first", list.size() > 0 ? list.get(0) : null);
			top3.put("second", list.size() > 1 ? list.get(1) : null);
			top3.put("third", list.size() > 2 ? list.get(2) : null);
		}
		return top3;
	}

	// 상위 3개 제외, 날짜순으로 페이지 단위 조회
	public List<StudyBoard> getPostsByPage(int page, int pageSize) {
		int offset = (page - 1) * pageSize + 3; // 상위 3개 제외
		return studyBoardMapper.getPostsByPage(offset, pageSize);
	}

	// 상위 3개 제외 후 게시글 총 개수
	public int countPosts() {
		return studyBoardMapper.countPosts() - 3;
	}
	
	public StudyBoard getPostById(int studyId) {
	    return studyBoardMapper.getPostById(studyId);
	}
}
