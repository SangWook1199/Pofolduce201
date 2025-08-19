package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.StudyBoardComment;
import org.kosa._musketeers.mapper.StudyBoardCommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudyBoardCommentService {
	private final StudyBoardCommentMapper studyBoardCommentMapper;

	public StudyBoardCommentService(StudyBoardCommentMapper studyBoardCommentMapper) {
		super();
		this.studyBoardCommentMapper = studyBoardCommentMapper;
	}

	// 댓글 작성 메서드입니다.
	public void createStudyComment(StudyBoardComment comment) {
		studyBoardCommentMapper.createStudyComment(comment);
	}

	// 댓글을 조회하는 메서드입니다.
	public List<StudyBoardComment> getCommentsByStudyIdWithPage(int studyId, int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return studyBoardCommentMapper.getStudyComments(studyId, pageSize, offset);
	}

	// 댓글의 갯수를 구하는 메서드입니다.
	public int countCommentsByStudyId(int studyId) {
		return studyBoardCommentMapper.countStudyComments(studyId);
	}

	// 댓글을 수정하는 메서드입니다.
	public void updateStudyComment(StudyBoardComment comment) {
		studyBoardCommentMapper.updateStudyComment(comment);
	}

	// 댓글을 삭제하는 메서드입니다.
	public void deleteStudyComment(int commentId) {
		studyBoardCommentMapper.deleteStudyComment(commentId);
	}

}
