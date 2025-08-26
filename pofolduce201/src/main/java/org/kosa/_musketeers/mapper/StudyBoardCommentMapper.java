package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.StudyBoardComment;

@Mapper
public interface StudyBoardCommentMapper {

	void createStudyComment(StudyBoardComment createComment);

	void updateStudyComment(StudyBoardComment comment);

	void deleteStudyComment(int commentsId);

	List<StudyBoardComment> getStudyComments(int studyId, int pageSize, int offset);

	int countStudyComments(int studyId);

	int getStudyCommentUserId(int commentId);

	StudyBoardComment getStudyCommentById(int commentId);

}
