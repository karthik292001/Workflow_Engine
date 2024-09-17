package com.example.TimesheetApproval.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TimesheetApproval.entities.ApprovalRequest;
import com.example.TimesheetApproval.entities.User;
import com.example.TimesheetApproval.entities.createApprovalRequest;
import com.example.TimesheetApproval.repositories.UserRepository;
import com.example.TimesheetApproval.repositories.WorkflowRepository;
import com.example.TimesheetApproval.services.ApprovalRequestService;
import com.example.TimesheetApproval.services.WorkflowService;



@RestController
@RequestMapping("/approvalRequest")
public class ApprovalRequestController {

    @Autowired
    private ApprovalRequestService approvalRequestService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkflowRepository workflowRepository;


    @PostMapping("/create")
    public ApprovalRequest createApprovalRequest(@RequestBody createApprovalRequest createApprovalRequest) {
        User employee = userRepository.findById(createApprovalRequest.getEmployeeId()).orElse(null);
        return approvalRequestService.createApprovalRequest(employee,createApprovalRequest.getStages() ,3L,createApprovalRequest.getFields());
       
    }

    @PostMapping("/approve/{approvalRequestId}/{approverId}/{status}")
    public String approveApprovalRequest(@PathVariable Long approvalRequestId, @PathVariable Long approverId,@PathVariable String status) {
        return approvalRequestService.approveApprovalRequest(approvalRequestId, approverId,status);
    }

    @GetMapping("/{id}")
    public ApprovalRequest getApprovalRequestById(@PathVariable Long id) {
        return approvalRequestService.findById(id);
    }

    @PostMapping("/approver/{approverId}")
    public List<ApprovalRequest> getApprovalRequestsForApprover(@PathVariable Long approverId) {
        return approvalRequestService.getApprovalRequestsForApprover(approverId);
    }
}