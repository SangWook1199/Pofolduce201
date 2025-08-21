package org.kosa._musketeers.controller;

import org.kosa._musketeers.service.StudyBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudyBoardRestController {
	private final StudyBoardService studyBoardService;

	public StudyBoardRestController(StudyBoardService studyBoardService) {
		super();
		this.studyBoardService = studyBoardService;
	}

	@PostMapping("/api/study/{studyId}/like")
    public ResponseEntity<Integer> addLike(@PathVariable("studyId") int studyId) {
        try {
            // Service 계층을 통해 좋아요 수 증가 로직 호출
            int newLikeCount = studyBoardService.addLike(studyId);
            return ResponseEntity.ok(newLikeCount);
        } catch (Exception e) {
            // 예외 발생 시 에러 응답
            return ResponseEntity.badRequest().body(-1);
        }
    }
}
