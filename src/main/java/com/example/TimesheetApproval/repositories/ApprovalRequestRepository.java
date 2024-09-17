package com.example.TimesheetApproval.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TimesheetApproval.entities.ApprovalRequest;

public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {
    List<ApprovalRequest> findByStatus(String status);
    List<ApprovalRequest> findByApprovalsApproverIdAndStatus(Long approverId, String status);
}