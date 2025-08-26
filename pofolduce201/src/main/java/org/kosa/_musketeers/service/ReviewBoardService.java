package org.kosa._musketeers.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.kosa._musketeers.domain.ReviewPost;
import org.kosa._musketeers.domain.ReviewPostComment;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.mapper.ReviewBoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewBoardService {

	private ReviewBoardMapper reviewBoardMapper;
	public ReviewBoardService(ReviewBoardMapper reviewBoardMapper) {
		this.reviewBoardMapper = reviewBoardMapper;
	}
	
	public List<ReviewPost> getReviewPostList(int page) {
		return reviewBoardMapper.getReviewPostList((page - 1) * 15, 15);
	}
	
	public int getTotalReviewPostCount() {
		return reviewBoardMapper.getTotalReviewPostCount();
	}

	public List<ReviewPost> getBestReviewPostList() {
		return reviewBoardMapper.getBestReviewPostList();
	}

	public void createPost(ReviewPost reviewPost) {
		reviewBoardMapper.createPost(reviewPost);
	}

	@Transactional
	public ReviewPost viewPost(int reviewPostId) {
		reviewBoardMapper.updateReviewPostViewCount(reviewPostId);
		return reviewBoardMapper.getReviewPostByReviewPostId(reviewPostId);
	}

	public void deleteReviewPost(int reviewPostId) {
		reviewBoardMapper.delteReviewPost(reviewPostId);
	}

	public ReviewPost getReviewPostById(int reviewPostId) {
		return reviewBoardMapper.getReviewPostByReviewPostId(reviewPostId);
	}

	public void editReviewPost(ReviewPost reviewPost) {
		reviewBoardMapper.updateReviewPostByReviewId(reviewPost);
	}

	public void writeComment(int userId, int reviewPostId, String comment) {
		User user = new User(userId);
		ReviewPostComment reviewPostComment = new ReviewPostComment(comment, user, reviewPostId);
		reviewBoardMapper.createReviewPostComment(reviewPostComment);
	}

	public List<ReviewPostComment> loadReviewPostCommentList(int reviewPostId, int start, int commentCount) {
		return reviewBoardMapper.getReviewCommentListByReviewPostId(reviewPostId, start, commentCount);
	}

	public void deleteComment(int reviewCommentId) {
		reviewBoardMapper.deleteReviewComment(reviewCommentId);
	}
	
	public void modifyComment(int reviewCommentId, String commentContents) {
		reviewBoardMapper.updateReviewComment(reviewCommentId, commentContents);
	}
	
	public int getTotalReviewPostCountById(int userId) {
		return reviewBoardMapper.getTotalReviewPostCountById(userId);
	}

	public String convertPdfToHtml(int portfolioId) {
		String html = "";
		File file = new File("src/main/resources/static/uploads/portfolio/" + portfolioId + ".pdf");
		if(!file.exists()) {
			return "<p style='color: red;'>해당 이력서 파일을 찾을 수 없습니다.</p>";
		}
		try(InputStream inputStream = Files.newInputStream(file.toPath())){
			// 1. PDF 문서 로드
			PDDocument pdf = PDDocument.load(inputStream);
			PDFDomTree parser = new PDFDomTree();
			// 2. HTML로 변환하기 위한 Writer 생성
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//3. PDFDomTree를 사용하여 HTML로 변환
	        try (PrintWriter output = new PrintWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8), true)){
				parser.writeText(pdf, output);
				html = new String(baos.toByteArray(), StandardCharsets.UTF_8);
	        } catch(Exception e) {
	        	html="fail";
	        }
		} catch(Exception e) {
			html = "fail";
		}
		return html;
	}

	//메인에 조회수 기준 베스트 게시글을 총 9개까지 보여주는 서비스 입니다.
	//홈 화면의 컨트롤러에서 불러옵니다.
	public List<Map<String, Object>> getReviewBoardByViewCount() {
		List<Map<String, Object>> list = reviewBoardMapper.getReviewBoardByViewCount();
		return list;
	}

	public int getTotalReviewCommentCount(int reviewPostId) {
		return reviewBoardMapper.getTotalReviewCommentCount(reviewPostId);
	}

	public int plusReviewPostLike(int reviewPostId) {
		reviewBoardMapper.updateReviewPostLike(reviewPostId);
		return reviewBoardMapper.selectLikeCountByReviewPostId(reviewPostId);
	}
	
	// 댓글 불러오기 관리자 페이지에서 게시물을 가져올때 사용합니다.
	public ReviewPostComment getReviewCommentById(int commentsId) {
	    return reviewBoardMapper.getReviewCommentById(commentsId);

	}
}
