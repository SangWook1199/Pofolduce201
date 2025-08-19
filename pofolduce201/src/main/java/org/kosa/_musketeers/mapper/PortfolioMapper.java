package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.Portfolio;

@Mapper
public interface PortfolioMapper {

	void createPortfolio(Portfolio portfolio);

	Portfolio getPortfolio(int portfolioId);

	List<Portfolio> getPortfolioList(int userId);

	void updateRepPortfolio(int userId, int portfolioId);

	void setRepPortfolio(int userId, int portfolioId);
	
	Integer getRepPortfolio(int userId);

	void deletePortfolio(int portfolioId);

}
