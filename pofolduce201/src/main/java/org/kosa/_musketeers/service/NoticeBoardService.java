package org.kosa._musketeers.service;

import java.time.LocalDateTime;
import java.util.List;

import org.kosa._musketeers.domain.NoticeBoard;
import org.kosa._musketeers.mapper.NoticeBoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoticeBoardService {
    private final NoticeBoardMapper noticeBoardMapper;

    public NoticeBoardService(NoticeBoardMapper noticeBoardMapper) {
    	super();
        this.noticeBoardMapper = noticeBoardMapper;
    }

    // 전체 게시물 수
    public int countPosts() {
        return noticeBoardMapper.countPosts();
    }

    // 최신순 페이징 조회
    public List<NoticeBoard> getPostsByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return noticeBoardMapper.getPostsByPage(offset, pageSize);
    }

    // 게시물 조회
    public NoticeBoard getPostById(int noticeId) {
        return noticeBoardMapper.getPostById(noticeId);
    }

    // 등록
    public void createPost(NoticeBoard noticeBoard) {
        noticeBoard.setNoticeCount(0); // 조회수 기본값
        noticeBoard.setDate(LocalDateTime.now()); // 등록일
        noticeBoardMapper.createPost(noticeBoard);
    }

    // 수정
    public void updatePost(NoticeBoard noticeBoard) {
        noticeBoardMapper.updatePost(noticeBoard);
    }

    // 삭제
    public void deletePost(int noticeId) {
        noticeBoardMapper.deletePost(noticeId);
    }
}
