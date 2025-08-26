package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.Report;
import org.kosa._musketeers.mapper.ReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportService {
	private final ReportMapper reportMapper;

	public ReportService(ReportMapper reportMapper) {
		super();
		this.reportMapper = reportMapper;
	}

	public void createReport(Report report) {
		reportMapper.createReport(report);
	}
	
	public List<Report> getAllReports() {
	    return reportMapper.getAllReports();
	}
	
	public Report getReportById(int reportId) {
	    return reportMapper.getReportById(reportId);
	}
	
	// 신고 상태 변경
	public void updateReportState(int reportId, String state) {
	    reportMapper.updateReportState(reportId, state);
	}
}
