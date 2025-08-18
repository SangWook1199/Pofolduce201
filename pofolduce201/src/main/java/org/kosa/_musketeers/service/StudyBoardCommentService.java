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


	public void createStudyComment(StudyBoardComment comment) {
		studyBoardCommentMapper.createStudyComment(comment);
	}
	
	public List<StudyBoardComment> getCommentsByStudyIdWithPage(int studyId, int page, int pageSize) {
	    int offset = (page - 1) * pageSize;
	    return studyBoardCommentMapper.getStudyComments(studyId, pageSize, offset);
	}

	public int countCommentsByStudyId(int studyId) {
	    return studyBoardCommentMapper.countStudyComments(studyId);
	}


	public void updateStudyComment(StudyBoardComment comment) {
		studyBoardCommentMapper.updateStudyComment(comment);
	}


	public void deleteStudyComment(int commentId) {
		studyBoardCommentMapper.deleteStudyComment(commentId);
	}

}
