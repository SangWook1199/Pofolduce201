package org.kosa._musketeers.service;

import java.util.List;

import org.kosa._musketeers.domain.Report;
import org.kosa._musketeers.domain.User;
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
    
    // 신고 상세 조회(ReportService에 위임)
    public Report getReportById(int reportId) {
        return reportService.getReportById(reportId);
    }
}
