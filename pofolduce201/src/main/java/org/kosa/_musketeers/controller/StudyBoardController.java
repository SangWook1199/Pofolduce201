package org.kosa._musketeers.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.kosa._musketeers.domain.StudyBoard;
import org.kosa._musketeers.domain.StudyBoardComment;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.StudyBoardCommentService;
import org.kosa._musketeers.service.StudyBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class StudyBoardController {
	private final StudyBoardService studyBoardService;
	private final StudyBoardCommentService studyBoardCommentService;

	public StudyBoardController(StudyBoardService studyBoardService,
			StudyBoardCommentService studyBoardCommentService) {
		super();
		this.studyBoardService = studyBoardService;
		this.studyBoardCommentService = studyBoardCommentService;
	}

	@GetMapping("/study")
	public String studyBoard(@RequestParam(defaultValue = "1") int page, Model model) {

		// 상위 3개 조회수
		Map<String, StudyBoard> top3Map = studyBoardService.getTop3ByViewCount();
		model.addAttribute("first", top3Map.get("first"));
		model.addAttribute("second", top3Map.get("second"));
		model.addAttribute("third", top3Map.get("third"));

		// 페이지네이션 적용 (17개)
		int pageSize = 17;
		List<StudyBoard> posts = studyBoardService.getPostsByPage(page, pageSize);
		model.addAttribute("dateList", posts);

		// 페이지 번호 계산
		int totalPosts = studyBoardService.countPosts();
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

		// 5페이지 단위로 나눠서 표시
		int startPage = ((page - 1) / 5) * 5 + 1;
		int endPage = Math.min(startPage + 4, totalPages);

		model.addAttribute("currentPage", page);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPages", totalPages);

		return "pages/study/study-board";
	}
	@GetMapping("/study/{studyId}")
	public String studyBoardPost(@PathVariable int studyId,
	                             @RequestParam(defaultValue = "1") int page,
	                             Model model,
	                             @SessionAttribute("userId") int userId) {

	    StudyBoard post = studyBoardService.getPostById(studyId);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    model.addAttribute("postDateFormatted", post.getPostDate().format(formatter));
	    model.addAttribute("post", post);

	    int pageSize = 5; // 한 페이지 댓글 수
	    List<StudyBoardComment> comments = studyBoardCommentService.getCommentsByStudyIdWithPage(studyId, page, pageSize);
	    model.addAttribute("comments", comments);

	    int totalComments = studyBoardCommentService.countCommentsByStudyId(studyId);
	    int totalPages = (int) Math.ceil((double) totalComments / pageSize);

	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);

	    model.addAttribute("userId", userId);

	    return "pages/study/study-board-post";
	}

	@PostMapping("/study/{studyId}/comments")
	public String addComment(@PathVariable("studyId") int studyId, @RequestParam("comment-content") String content,
			@RequestParam("userId") int userId) {
		try {
			StudyBoard studyBoard = new StudyBoard();
			studyBoard.setStudyId(studyId);

			User user = new User();
			user.setUserId(userId);

			StudyBoardComment comment = new StudyBoardComment(content, studyBoard, user);
			studyBoardCommentService.createStudyComment(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/study/" + studyId;
	}

	@PostMapping("/study/{studyId}/comments/{commentId}/edit")
	public String editComment(@PathVariable int studyId, @PathVariable int commentId,
			@RequestParam("comment-content") String content, @SessionAttribute("userId") int userId) {
		StudyBoardComment comment = new StudyBoardComment();
		comment.setCommentsId(commentId);
		comment.setCommentsContents(content);
		
		studyBoardCommentService.updateStudyComment(comment);
	    return "redirect:/study/" + studyId;
	}
	
	@PostMapping("/study/{studyId}/comments/{commentId}/delete")
	public String deleteComment(
	        @PathVariable int studyId,
	        @PathVariable int commentId,
	        @SessionAttribute("userId") int userId) {

	    studyBoardCommentService.deleteStudyComment(commentId);
	    return "redirect:/study/" + studyId;
	}
}
