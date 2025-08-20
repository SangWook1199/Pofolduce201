package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.kosa._musketeers.domain.NoticeBoard;

@Mapper
public interface NoticeBoardMapper {

    // 공지사항 등록 (Create)
    void createPost(NoticeBoard noticeBoard);

    // 공지사항 단건 조회 (Read)
    NoticeBoard getPostById(int noticeId);

    // 공지사항 목록 조회 (페이징)
    List<NoticeBoard> getPostsByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    // 공지사항 총 개수 조회
    int countPosts();

    // 공지사항 삭제 (Delete)
    void deletePost(int noticeId);

    // 공지사항 수정 (Update)
    void updatePost(NoticeBoard noticeBoard);

    // 조회수 순 정렬 조회
    List<NoticeBoard> getPostsOrderByViewCount();
    
    
}
