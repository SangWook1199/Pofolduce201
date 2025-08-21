package org.kosa._musketeers.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.Report;

@Mapper
public interface ReportMapper {

	void createReport(Report report);

}
