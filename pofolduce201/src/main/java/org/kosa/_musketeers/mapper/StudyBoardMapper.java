package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.StudyBoard;

@Mapper
public interface StudyBoardMapper {

	void createPost(StudyBoard studyboard1);

	List<StudyBoard> getPostOrderByViewCount();

	List<StudyBoard> getPostOrderByDate();

	List<StudyBoard> getPostsByPage(int offset, int size);

	int countPosts();

	StudyBoard getPostById(int studyId);

}
