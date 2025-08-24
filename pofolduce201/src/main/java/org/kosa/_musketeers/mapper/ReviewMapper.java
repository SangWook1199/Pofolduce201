package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.Review;

@Mapper
public interface ReviewMapper {

	public void insertReview(Review review);

	public List<Review> selectAllReviews();
}
