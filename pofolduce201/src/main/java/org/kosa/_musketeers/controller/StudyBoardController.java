package org.kosa._musketeers.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

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

	// 스터디 게시판 페이지
	@GetMapping("/study")
	public String studyBoard(@SessionAttribute(name = "userId", required = false) Integer userId,
			@RequestParam(defaultValue = "1") int page, Model model, HttpServletRequest request) {
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
		int totalPages = 0;

		// 총 게시글 수가 3개 초과일 때만 페이지네이션을 계산
		if (totalPosts > 0) {
			totalPages = (int) Math.ceil((double) totalPosts / pageSize);
		}

		// 5페이지 단위로 나눠서 표시
		int startPage = ((page - 1) / 5) * 5 + 1;
		int endPage = Math.min(startPage + 4, totalPages);

		model.addAttribute("currentPage", page);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentUri", request.getRequestURI());

		return "pages/study/study-board";
	}

	// 지도
	@GetMapping("/study/api/map")
	@ResponseBody
	public List<Map<String, Object>> getAllPostData() {
		List<StudyBoard> allPosts = studyBoardService.getAllPosts(); // 모든 게시글 가져오는 서비스 메서드
		List<Map<String, Object>> result = new ArrayList<>();

		for (StudyBoard post : allPosts) {
			if (post.getAddress() != null && !post.getAddress().isEmpty()) {
				Map<String, Object> postData = new HashMap<>();
				postData.put("address", post.getAddress());
				postData.put("studyId", post.getStudyId());
				postData.put("title", post.getTitle());
				result.add(postData);
			}
		}
		return result;
	}

	// 스터디 게시글 상세 페이지
	@GetMapping("/study/{studyId}")
	public String studyBoardPost(@PathVariable int studyId, @RequestParam(defaultValue = "1") int page, Model model,
			@SessionAttribute(name = "userId", required = false) Integer userId, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		StudyBoard post = studyBoardService.getPostById(studyId);
		if (post == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "게시글이 존재하지 않습니다.");
			return "redirect:/study";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		model.addAttribute("postDateFormatted", post.getPostDate().format(formatter));
		model.addAttribute("post", post);
		int pageSize = 5; // 한 페이지 댓글 수
		List<StudyBoardComment> comments = studyBoardCommentService.getCommentsByStudyIdWithPage(studyId, page,
				pageSize);
		model.addAttribute("comments", comments);

		int totalComments = studyBoardCommentService.countCommentsByStudyId(studyId);
		int totalPages = 0; // 초기값을 0으로 설정

		if (totalComments > 0) {
			totalPages = (int) Math.ceil((double) totalComments / pageSize);
		}
		studyBoardService.addViewCount(studyId);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("userId", userId);
		model.addAttribute("currentUri", request.getRequestURI());

		return "pages/study/study-board-post";
	}

	// 댓글 작성
	@PostMapping("/study/{studyId}/comments")
	public String addComment(@PathVariable("studyId") int studyId, @RequestParam("comment-content") String content,
			@RequestParam("userId") Integer userId, RedirectAttributes redirectAttributes) {
		StudyBoard studyBoard = new StudyBoard();
		studyBoard.setStudyId(studyId);

		User user = new User();
		user.setUserId(userId);

		StudyBoardComment comment = new StudyBoardComment(content, studyBoard, user);
		studyBoardCommentService.createStudyComment(comment);
		redirectAttributes.addFlashAttribute("successMessage", "댓글을 성공적으로 작성하였습니다.");

		return "redirect:/study/" + studyId;
	}

	// 댓글 수정
	@PostMapping("/study/{studyId}/comments/{commentId}/edit")
	public String editComment(@PathVariable int studyId, @PathVariable int commentId,
			@RequestParam("comment-content") String content,
			@SessionAttribute(name = "userId", required = false) Integer userId,
			RedirectAttributes redirectAttributes) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}

		int commentUserId = studyBoardCommentService.getStudyCommentUserId(commentId);
		if (commentUserId != userId) {
			redirectAttributes.addFlashAttribute("errorMessage", "댓글을 작성한 회원이 아닙니다.");
			return "redirect:/study/" + studyId;
		}

		StudyBoardComment comment = new StudyBoardComment();
		comment.setCommentsId(commentId);
		comment.setCommentsContents(content);

		studyBoardCommentService.updateStudyComment(comment);
		redirectAttributes.addFlashAttribute("successMessage", "댓글을 성공적으로 수정하였습니다.");
		return "redirect:/study/" + studyId;
	}

	// 댓글 삭제
	@PostMapping("/study/{studyId}/comments/{commentId}/delete")
	public String deleteComment(@PathVariable int studyId, @PathVariable int commentId,
			@SessionAttribute(name = "userId", required = false) Integer userId,
			RedirectAttributes redirectAttributes) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		int commentUserId = studyBoardCommentService.getStudyCommentUserId(commentId);
		if (commentUserId != userId) {
			redirectAttributes.addFlashAttribute("errorMessage", "댓글을 작성한 회원이 아닙니다.");
			return "redirect:/study/" + studyId;
		}
		studyBoardCommentService.deleteStudyComment(commentId);
		redirectAttributes.addFlashAttribute("successMessage", "댓글을 성공적으로 삭제하였습니다.");

		return "redirect:/study/" + studyId;
	}

	// 게시글 작성 페이지
	@GetMapping("/study/write")
	public String getStudyBoardWrite(@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		return "pages/study/study-board-write";
	}

	// 게시글 작성
	@PostMapping("/study/write")
	public String writeSubmit(@ModelAttribute StudyBoard board,
			@SessionAttribute(name = "userId", required = false) Integer userId,
			RedirectAttributes redirectAttributes) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		User user = new User();
		user.setUserId(userId);

		board.setUserId(user);
		studyBoardService.createPost(board);
		return "redirect:/study";
	}

	// 게시글 수정 페이지
	@GetMapping("/study/{studyId}/update")
	public String editStudyPost(@PathVariable int studyId, Model model,
			@SessionAttribute(name = "userId", required = false) Integer userId) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		int postUserId = studyBoardService.getStudyPostUserId(studyId); // 서비스에 메서드 추가 필요
		if (postUserId != userId) {
			return "redirect:/study/" + studyId;
		}
		StudyBoard post = studyBoardService.getPostById(studyId);
		model.addAttribute("post", post);

		return "pages/study/study-board-update";
	}

	// 게시글 수정
	@PostMapping("/study/{studyId}/update")
	public String editPost(@PathVariable int studyId, @ModelAttribute StudyBoard board,
			@SessionAttribute(name = "userId", required = false) Integer userId,
			RedirectAttributes redirectAttributes) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		int postUserId = studyBoardService.getStudyPostUserId(studyId); // 서비스에 메서드 추가 필요
		if (postUserId != userId) {
			redirectAttributes.addFlashAttribute("errorMessage", "게시글을 작성한 회원이 아닙니다.");
			return "redirect:/study/" + studyId;
		}
		studyBoardService.updatePost(board);

		return "redirect:/study/" + studyId;
	}

	// 게시글 삭제
	@PostMapping("/study/{studyId}/delete")
	public String deletePost(@PathVariable int studyId,
			@SessionAttribute(name = "userId", required = false) Integer userId,
			RedirectAttributes redirectAttributes) {
		if (userId == null) {
			return "redirect:/not-logined?msg=login_required";
		}
		int postUserId = studyBoardService.getStudyPostUserId(studyId); // 서비스에 메서드 추가 필요
		if (postUserId != userId) {
			redirectAttributes.addFlashAttribute("errorMessage", "게시글을 작성한 회원이 아닙니다.");
			return "redirect:/study/" + studyId;
		}
		studyBoardService.deletePost(studyId);
		redirectAttributes.addFlashAttribute("successMessage", "게시글을 성공적으로 삭제하였습니다.");

		return "redirect:/study";
	}
}
