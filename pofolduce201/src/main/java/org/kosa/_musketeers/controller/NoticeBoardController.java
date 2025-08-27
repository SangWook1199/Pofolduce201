package org.kosa._musketeers.controller;

import java.util.List;

import org.kosa._musketeers.domain.NoticeBoard;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.service.NoticeBoardService;
import org.kosa._musketeers.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final UserService userService;

    public NoticeBoardController(NoticeBoardService noticeBoardService, UserService userService) {
        this.noticeBoardService = noticeBoardService;
        this.userService = userService;
    }

    // 공지사항 목록 페이지
    @GetMapping("/notice-board")
    public String noticeBoard(@RequestParam(defaultValue = "1") int page,
                              @SessionAttribute(required=false) Integer userId,
                              Model model, HttpServletRequest request) {

        boolean isAdmin = false;

        if (userId != null) {
            User user = userService.getUserById(userId);
            if (user != null && "관리자".equals(user.getUserType())) {
                isAdmin = true;
            }
        }
        model.addAttribute("isAdmin", isAdmin);

        int pageSize = 15;
        List<NoticeBoard> posts = noticeBoardService.getPostsByPage(page, pageSize);
        model.addAttribute("posts", posts);

        int totalPosts = noticeBoardService.countPosts();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        int startPage = ((page - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPages);

        model.addAttribute("currentPage", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/notice/notice-board";
    }

    // 공지사항 상세 페이지
    @GetMapping("/notice/{noticeId}")
    public String noticeBoardPost(@PathVariable int noticeId,
                                  @SessionAttribute(required=false) Integer userId,
                                  Model model, HttpServletRequest request) {

        boolean isAdmin = false;
        if (userId != null) {
            User user = userService.getUserById(userId);
            if (user != null && "관리자".equals(user.getUserType())) {
                isAdmin = true;
            }
        }

        NoticeBoard post = noticeBoardService.getPostById(noticeId);
        model.addAttribute("post", post);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/notice/notice-board-post";
    }

    // 공지사항 작성 폼
    @GetMapping("/notice/notice-post-create")
    public String showCreateForm(@SessionAttribute(required=false) Integer userId,
                                 Model model) {

        if (userId == null) return "redirect:/notice-board";

        User user = userService.getUserById(userId);
        if (user == null || !"관리자".equals(user.getUserType())) {
            return "redirect:/notice-board";
        }

        model.addAttribute("noticeBoard", new NoticeBoard());
        return "pages/notice/notice-post-create";
    }

    // 공지사항 작성 처리
    @PostMapping("/notice/notice-post-create")
    public String writeNoticeSubmit(@ModelAttribute NoticeBoard noticeBoard,
                                    @SessionAttribute(required=false) Integer userId) {

        if (userId == null) return "redirect:/notice-board";

        User user = userService.getUserById(userId);
        if (user == null || !"관리자".equals(user.getUserType())) {
            return "redirect:/notice-board";
        }

        noticeBoard.setUserId(user);
        noticeBoardService.createPost(noticeBoard);

        return "redirect:/notice-board";
    }

    // 공지사항 수정 폼
    @GetMapping("/notice/{noticeId}/update")
    public String getNoticeUpdateForm(@PathVariable int noticeId,
                                      @SessionAttribute(required=false) Integer userId,
                                      Model model) {

        if (userId == null) return "redirect:/notice-board";

        User user = userService.getUserById(userId);
        if (user == null || !"관리자".equals(user.getUserType())) {
            return "redirect:/notice-board";
        }

        NoticeBoard notice = noticeBoardService.getPostById(noticeId);
        model.addAttribute("notice", notice);
        return "pages/notice/notice-post-update";
    }

    // 공지사항 수정 처리
    @PostMapping("/notice/{noticeId}/update")
    public String updateNoticeSubmit(@PathVariable int noticeId,
                                     @ModelAttribute NoticeBoard noticeBoard,
                                     @SessionAttribute(required=false) Integer userId) {

        if (userId == null) return "redirect:/notice-board";

        User user = userService.getUserById(userId);
        if (user == null || !"관리자".equals(user.getUserType())) {
            return "redirect:/notice-board";
        }

        noticeBoard.setUserId(user);
        noticeBoard.setNoticeId(noticeId);

        NoticeBoard existingNotice = noticeBoardService.getPostById(noticeId);
        noticeBoard.setDate(existingNotice.getDate());

        noticeBoardService.updatePost(noticeBoard);

        return "redirect:/notice/" + noticeId;
    }

    // 공지사항 삭제
    @PostMapping("/notice/{noticeId}/delete")
    public String deleteNotice(@PathVariable int noticeId,
                               @SessionAttribute(required=false) Integer userId) {

        if (userId == null) return "redirect:/notice-board";

        User user = userService.getUserById(userId);
        if (user == null || !"관리자".equals(user.getUserType())) {
            return "redirect:/notice-board";
        }

        noticeBoardService.deletePost(noticeId);
        return "redirect:/notice-board";
    }
}
