//package org.kosa._musketeers.review;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.kosa._musketeers.domain.ReviewPost;
//import org.kosa._musketeers.mapper.ReviewBoardMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//public class ReviewBoardTest {
//	
//	@Autowired
//	ReviewBoardMapper reviewBoardMapper;
//
//	@Test
//	public void getReviewPostListTest() {
//		
//		List<ReviewPost> list = reviewBoardMapper.getReviewPostList(1, 15);
//		for(ReviewPost post: list) {
//			System.out.println(post.getTitle());
//		}
//		assertThat(list.size()).isNotEqualTo(0);
//	}
//}
