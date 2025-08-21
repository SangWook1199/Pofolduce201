package org.kosa._musketeers.controller;

import java.util.HashMap;
import java.util.Map;

import org.kosa._musketeers.service.StudyBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudyBoardRestController {
	private final StudyBoardService studyBoardService;

	public StudyBoardRestController(StudyBoardService studyBoardService) {
		super();
		this.studyBoardService = studyBoardService;
	}

	@PostMapping("/study/{studyId}/like")
	public ResponseEntity<Map<String, Object>> likePost(@PathVariable int studyId,
			@RequestBody Map<String, Boolean> body) {
		
		boolean liked = body.getOrDefault("liked", false);
		
		int updatedLikeCount;
        if(liked) {
            // 현재 페이지에서 이미 눌러서 -> 취소
            updatedLikeCount = studyBoardService.decreaseLike(studyId);
        } else {
            // 좋아요
            updatedLikeCount = studyBoardService.increaseLike(studyId);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("likeCount", updatedLikeCount);

		return ResponseEntity.ok(result);
	}
}
