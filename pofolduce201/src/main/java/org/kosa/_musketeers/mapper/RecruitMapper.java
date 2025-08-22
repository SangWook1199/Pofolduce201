package org.kosa._musketeers.mapper;

import java.util.List;
import java.util.Map;

public interface RecruitMapper {

	void saveRecruit(String title, String company, String content, String link, String imgLink);

	List<Map<String, String>> getRecruit();

	

}
