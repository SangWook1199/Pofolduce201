package org.kosa._musketeers.test.studyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.kosa._musketeers.domain.StudyBoard;
import org.kosa._musketeers.domain.StudyBoardComment;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.StudyBoardCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudyBoardCommentTest {
	
	@Autowired
	private StudyBoardCommentMapper studyBoardCommentMapper;
	
	@Test
	void createBoardComment() {
		LocalDateTime dateTime = LocalDateTime.of(2025, 8, 16, 20, 30, 45);
		User user = new User(1,"aaa","aaa","fd","관리자","no","a",2,43,"정상",12,dateTime,"aa");
		StudyBoard studyBoard = new StudyBoard(1,"A",3,"Aaa",dateTime,3,"aa",user);
		StudyBoardComment createComment = new StudyBoardComment("aaa",studyBoard,user);
		studyBoardCommentMapper.createStudyComment(createComment);
		
		assertThat(createComment).isNotNull();
	}
	
//	@Test
//	void getBoardCommentList() {
//		int studyId = 40;
//		List<StudyBoardComment> list = studyBoardCommentMapper.getStudyComments(studyId);
//		
//		assertThat(list).isNotNull();
//	}
	
	@Test
	void updateComment() {
		LocalDateTime dateTime = LocalDateTime.of(2025, 8, 16, 20, 30, 45);
		User user = new User(1,"aaa","aaa","fd","관리자","no","a",2,43,"정상",12,dateTime,"aa");
		StudyBoard studyBoard = new StudyBoard(1,"A",3,"Aaa",dateTime,3,"aa",user);
		StudyBoardComment comment = new StudyBoardComment("aaa",studyBoard,user);
		studyBoardCommentMapper.updateStudyComment(comment);
	};
	
	@Test 
	void deleteComment(){
		int commentsId = 1;
		studyBoardCommentMapper.deleteStudyComment(commentsId);
	}

}
