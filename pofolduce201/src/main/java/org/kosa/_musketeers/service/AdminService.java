package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.Report;
import org.kosa._musketeers.domain.User;
import org.kosa._musketeers.domain.Verification;
import org.kosa._musketeers.mapper.AdminMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

	private final AdminMapper adminMapper;
	private final ReportService reportService;

	public AdminService(AdminMapper adminMapper, ReportService reportService) {
		this.adminMapper = adminMapper;
		this.reportService = reportService;
	}

	// 전체 유저 조회
	public List<User> getAllUsers() {
		return adminMapper.getAllUsers();
	}

	// 특정 유저 조회
	public User getUserById(int userId) {
		return adminMapper.getUserById(userId);
	}

	// 유저 제재 (기간 설정 + 횟수 증가)
	public void sanctionUser(int userId, int days) {
		adminMapper.updateSanction(userId, days, "정지");
		adminMapper.increaseSanctionCount(userId);
	}

	// 신고 상세 조회
	public Report getReportById(int reportId) {
		return reportService.getReportById(reportId);
	}

	// 회사 인증 내역 전체 조회
	public List<Verification> getAllVerifications() {
		return adminMapper.getAllVerifications();
	}

	// 회사 인증 상태 업데이트
	public void updateVerificationStatus(int userId, String status) {
		adminMapper.updateVerificationStatus(userId, status);
	}

	// 유저 회사 인증 정보 업데이트 (예: 인증 완료 시 유저 회사 정보 및 인증 상태 업데이트)
	public void updateUserCompanyInfo(int userId, String companyVerify, String companyName) {
		adminMapper.updateUserCompanyInfo(userId, companyVerify, companyName);
	}

	// 유저 인증 상세 조회
	public Verification getVerificationByUserId(int userId) {
		return adminMapper.getVerificationByUserId(userId);
	}
}
