package org.kosa._musketeers.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kosa._musketeers.domain.MyPageComment;
import org.kosa._musketeers.domain.MyPagePost;
import org.kosa._musketeers.domain.MyPageReviewComment;
import org.kosa._musketeers.domain.MyPageStudyComment;
import org.kosa._musketeers.domain.Review;
import org.kosa._musketeers.domain.ReviewBoard;
import org.kosa._musketeers.domain.StudyBoard;
import org.kosa._musketeers.mapper.MyBoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 마이페이지에서 사용되는 서비스 입니다.
 */
@Service
@Transactional
public class MyBoardService {

	private final MyBoardMapper myBoardMapper;

	public MyBoardService(MyBoardMapper myBoardMapper) {
		super();
		this.myBoardMapper = myBoardMapper;
	}

	// 마이페이지에서 내가 작성한 게시글을 전체 조회하는 메소드 입니다.
	public List<MyPagePost> findMyPost(int userId, int page, int size) {
	    List<MyPagePost> result = new ArrayList<>();

		// 첨삭게시판에서 게시글 찾기
		for (ReviewBoard rb : myBoardMapper.findMyReviewPost(userId)) {
			
			result.add(new MyPagePost(rb.getReviewPostId(), "review", // 게시판 타입
					rb.getTitle(), rb.getViewCount(), rb.getPostHtml(), rb.getCreateDate(), rb.getLikeCount(),
					rb.getUserId(), rb.getPortfolioId(), // Review 전용
					null // Study 전용 없음
			));
			
		}

		// 스터디게시판에서 게시글 찾기

		for (StudyBoard sb : myBoardMapper.findMyStudyPost(userId)) {
			if (sb.getUserId() != null) {   // User 객체가 있을 때만 추가
			result.add(new MyPagePost(sb.getStudyId(), "study", // 게시판 타입
					sb.getTitle(), sb.getViewCount(), sb.getPostHtml(), sb.getPostDate(), sb.getLikeCount(),
					sb.getUserId().getUserId(), null, // Review 전용 없음
					sb.getAddress() // Study 전용
			));
			}
		}

		// 최신순으로 정렬한다.
		result.sort(Comparator.comparing(MyPagePost::getCreateDate).reversed());

	    // 전체 게시글 개수
	    int totalCount = result.size();

	    // 페이징 (subList 사용)
	    int fromIndex = Math.max(0, (page - 1) * size);
	    int toIndex = Math.min(fromIndex + size, totalCount);

	    if (fromIndex > toIndex) {
	        return new ArrayList<>(); // 빈 리스트
	    }

	    return result.subList(fromIndex, toIndex);

	}
	
	// 게시글 총 개수
	public int countMyPost(int userId) {
	    int reviewCount = myBoardMapper.findMyReviewPost(userId).length;
	    int studyCount = myBoardMapper.findMyStudyPost(userId).length;
	    return reviewCount + studyCount;
	}

	// 마이페이지에서 내가 작성한 댓글을 조회하는 메소드 입니다.
	public List<MyPageComment> findMyComment(int userId , int page, int size) {
		List<MyPageComment> result = new ArrayList<>();

		// 첨삭게시판에서 게시글 찾기
		for (MyPageReviewComment rc : myBoardMapper.findMyReviewComment(userId)) {
			result.add(new MyPageComment(rc.getCommentsId(), "review", // 게시판 타입
					rc.getCommentsContents(), rc.getCommentsDate(), rc.getUserId(), rc.getReviewId(), // Review 전용
					null // Study 전용 없음
			));
		}

		// 스터디게시판에서 게시글 찾기
		for (MyPageStudyComment sc : myBoardMapper.findMyStudyComment(userId)) {
			result.add(new MyPageComment(sc.getCommentsId(), "study", // 게시판 타입
					sc.getCommentsContents(), sc.getCommentsDate(), sc.getUserId(), null, // Review 전용
					sc.getStudyId() // Study 전용 없음
			));
		}

		// 최신순으로 정렬한다.
		result.sort(Comparator.comparing(MyPageComment::getCommentsDate).reversed());
		
		 // 전체 댓글 개수
	    int totalCount = result.size();

	    // 페이징 (subList 사용)
	    int fromIndex = Math.max(0, (page - 1) * size);
	    int toIndex = Math.min(fromIndex + size, totalCount);

	    if (fromIndex > toIndex) {
	        return new ArrayList<>(); // 빈 리스트
	    }

		return result.subList(fromIndex, toIndex);


	}
	
	// 댓글 총 개수
		public int countMyComment(int userId) {
		    int reviewCount = myBoardMapper.findMyReviewComment(userId).length;
		    int studyCount = myBoardMapper.findMyStudyComment(userId).length;
		    return reviewCount + studyCount;
		}

	// 마이페이지에서 내가 작성한 첨삭을 조회하는 메소드 입니다.
	public List<Review> findMyReview(int userId, int page, int size) {

		List<Review> result = new ArrayList<>();

		result = myBoardMapper.findMyReview(userId);

		 // 전체 첨삭 개수
	    int totalCount = result.size();

	    // 페이징 (subList 사용)
	    int fromIndex = Math.max(0, (page - 1) * size);
	    int toIndex = Math.min(fromIndex + size, totalCount);

	    if (fromIndex > toIndex) {
	        return new ArrayList<>(); // 빈 리스트
	    }

		return result.subList(fromIndex, toIndex);
	}
	
	// 첨삭 총 개수
	public int countMyReview(int userId) {
	    int reviewCount = myBoardMapper.findMyReview(userId).size();
	    return reviewCount ;
	}

}
