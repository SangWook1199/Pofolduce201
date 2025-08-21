package org.kosa._musketeers.service;

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
	
	
}
