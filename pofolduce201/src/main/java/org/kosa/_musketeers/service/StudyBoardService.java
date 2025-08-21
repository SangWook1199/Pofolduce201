package org.kosa._musketeers.service;

import java.time.LocalDateTime;
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

	// 조회수 3위까지 조회하는 메서드입니다.
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

	// 상위 3개 제외, 날짜순으로 페이지 단위로 조회하는 메서드입니다.
	public List<StudyBoard> getPostsByPage(int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return studyBoardMapper.getPostsByPage(offset, pageSize);
	}

	// 상위 3개 제외 후 게시글 총 개수를 구하는 메서드입니다.
	public int countPosts() {
		return studyBoardMapper.countPosts() - 3;
	}

	// 게시글 조회하는 메서드입니다.
	public StudyBoard getPostById(int studyId) {
		return studyBoardMapper.getPostById(studyId);
	}

	// 게시글 작성 메서드입니다.
	public void createPost(StudyBoard board) {
		board.setViewCount(0);
		board.setLikeCount(0);
		board.setPostDate(LocalDateTime.now());

		studyBoardMapper.createPost(board);
	}

	// 게시글 수정 메서드입니다.
	public void updatePost(StudyBoard board) {
		studyBoardMapper.updatePost(board);
	}

	// 게시글 삭제 메서드입니다.
	public void deletePost(int studyId) {
		studyBoardMapper.deletePost(studyId);
	}

	public List<Map<String, Object>> getStudyBoardByViewCount() {
		List<Map<String, Object>> list = studyBoardMapper.getStudyBoardByViewCount();
		return list;
	}

	public int decreaseLike(int studyId) {
		studyBoardMapper.updateLikeCount(studyId, studyBoardMapper.getLikeCount(studyId) + 1);
        return studyBoardMapper.getLikeCount(studyId);
	}

	public int increaseLike(int studyId) {
		studyBoardMapper.updateLikeCount(studyId, studyBoardMapper.getLikeCount(studyId) - 1);
        return studyBoardMapper.getLikeCount(studyId);
	}

}
