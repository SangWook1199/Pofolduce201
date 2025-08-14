package org.kosa._musketeers.test.studyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.kosa._musketeers.domain.StudyBoard;
import org.kosa._musketeers.mapper.StudyBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StudyBoardTest {
	
	@Autowired
	private StudyBoardMapper studyBoardMapper;
	
	@Test
	void getBoardList() {
		// Given
		StudyBoard studyboard1 = new StudyBoard("재능교육1",123,"야이야",999,"혜화",2);
		StudyBoard studyboard2 = new StudyBoard("재능교육3",1234,"야이야",999,"혜화",5);
		StudyBoard studyboard3 = new StudyBoard("재능교육2",1223,"야이야",999,"혜화",6);
		StudyBoard studyboard4 = new StudyBoard("재능교육4",1123,"야이야",999,"혜화",7);
		
		studyBoardMapper.createPost(studyboard1);
		studyBoardMapper.createPost(studyboard2);
		studyBoardMapper.createPost(studyboard3);
		studyBoardMapper.createPost(studyboard4);
		
		// When
		List<StudyBoard> allPostsOrderByViewCount = studyBoardMapper.getPostOrderByViewCount();
		List<StudyBoard> allPostsOrderByDate = studyBoardMapper.getPostOrderByDate();
		
		// Then
		for (StudyBoard post : allPostsOrderByViewCount) {
		    System.out.println(post);
		}
		for (StudyBoard post : allPostsOrderByDate) {
		    System.out.println(post);
		}
		 assertThat(allPostsOrderByViewCount.get(0).getAddress()).isEqualTo(studyboard1.getAddress());
	}
	
	@Test
    void testPagination() {
        // Given: 이미 DB에 충분한 데이터가 있다고 가정
        int page = 1;
        int pageSize = 17;
        int offset = 3; // 상위 3개 제외
        List<StudyBoard> pagedPosts = studyBoardMapper.getPostsByPage(offset, pageSize);

        // Then
        assertThat(pagedPosts.size()).isLessThanOrEqualTo(pageSize);
    }
}
