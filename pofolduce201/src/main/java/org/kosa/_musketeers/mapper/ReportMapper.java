package org.kosa._musketeers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa._musketeers.domain.Report;

@Mapper
public interface ReportMapper {

	void createReport(Report report);

	// 신고 전체 페이지
	List<Report> getAllReports();
	
	// 신고 상세페이지
	Report getReportById(int reportId);

	void updateReportState(int reportId, String state);
}
