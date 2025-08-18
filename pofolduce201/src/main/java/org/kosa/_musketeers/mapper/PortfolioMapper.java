package org.kosa._musketeers.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.Portfolio;

@Mapper
public interface PortfolioMapper {

	void createPortfolio(Portfolio portfolio);

	Portfolio getPortfolio(int portfolioId);

}
