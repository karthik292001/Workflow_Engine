package com.example.TimesheetApproval.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TimesheetApproval.entities.Approval;
import com.example.TimesheetApproval.entities.ApprovalRequest;
import com.example.TimesheetApproval.entities.FieldsValues;
import com.example.TimesheetApproval.entities.User;
import com.example.TimesheetApproval.entities.Workflow;
import com.example.TimesheetApproval.repositories.ApprovalRequestRepository;
import com.example.TimesheetApproval.repositories.UserRepository;



@Service
public class ApprovalRequestService {

    @Autowired
    private ApprovalRequestRepository approvalRequestRepository;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private UserRepository userRepository;

    public ApprovalRequest createApprovalRequest(User employee,int stages, Long workflowId,Set<FieldsValues> fieldsValues) {

        Workflow workflow = workflowService.getWorkflow(workflowId);
        System.out.println(workflow.getStages().size());
        ApprovalRequest approvalRequest = new ApprovalRequest();
        approvalRequest.setEmployee(employee);
        approvalRequest.setStatus("Pending");
        approvalRequest.setStages(new ArrayList<>(workflow.getStages()));
        approvalRequest.setWorkflow(workflow);

        List<String> approvalStages = new ArrayList<>();
        for (int i = 0; i < stages; i++) {
            if (i < workflow.getStages().size()) {
                approvalStages.add(workflow.getStages().get(i));
            }
        }
        // Set the stages list in ApprovalRequest
        approvalRequest.setStages(approvalStages);


        User lead = userRepository.findById(employee.getLead().getId()).get();
        User manager = userRepository.findById(lead.getManager().getId()).get();
        User ceo = userRepository.findById(manager.getCeo().getId()).get();
        // Initialize statuses based on workflow stages
        for (String stage : approvalRequest.getStages()) {
            switch (stage) {
                case "lead":
                    approvalRequest.setLeadStatus("Pending");
                    approvalRequest.setLeadApprover(lead);
                    break;
                case "manager":
                    approvalRequest.setManagerStatus("Pending");
                    approvalRequest.setManagerApprover(manager);
                    break;
                case "ceo":
                    approvalRequest.setCeoStatus("Pending");
                    approvalRequest.setCeoApprover(ceo);
                    break;
            }
        }



        for (FieldsValues fieldValue : fieldsValues) {
            fieldValue.setApprovalRequest(approvalRequest);
        }
        
        approvalRequest.setFieldsValues(fieldsValues);
        approvalRequestRepository.save(approvalRequest);
        return approvalRequest;
    }

    public String approveApprovalRequest(Long approvalRequestId, Long approverId,String status) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        User approver = userRepository.findById(approverId).orElse(null);
        Workflow workflow = approvalRequest.getWorkflow();
        List<Approval> approvals = approvalRequest.getApprovals();

        if (approvalRequest == null || approver == null) {
            return "ApprovalRequest or approver not found.";
        }
        String currentStage;
        try {
            currentStage = workflow.getStages().get(approvals.size());
        } catch (Exception e) {
            return "All stages are completed";
        }
        if (approver.getRole().equalsIgnoreCase(currentStage)) {

            Approval approval = new Approval();
            approval.setApprover(approver);
            approval.setStatus("Approved");
            approval.setApprovalRequest(approvalRequest);
            

            // Update status based on role
            switch (approver.getRole()) {
                case "lead":
                    if (approver.getId() == approvalRequest.getLeadApprover().getId()) {
                        if (status.equalsIgnoreCase("approve")) {
                            approvalRequest.setLeadStatus("Approved");
                            approvals.add(approval);
                            approvalRequest.setApprovals(approvals);
                        } else if (status.equalsIgnoreCase("reject")) {
                            approvalRequest.setLeadStatus("Rejected");
                            
                        } else {
                            return "Invalid status";
                        }
                    } else {
                        return "You are not allowed to update status1";
                    }
                    break;
                case "manager":
                    if(approver.getId()==approvalRequest.getManagerApprover().getId())
                    {
                        if (status.equalsIgnoreCase("approve")) {
                            approvalRequest.setManagerStatus("Approved");
                            approvals.add(approval);
                            approvalRequest.setApprovals(approvals);
                        } else if (status.equalsIgnoreCase("reject")) {
                            approvalRequest.setManagerStatus("Rejected");
                            approvalRequest.setLeadStatus("Pending");
                            approvalRequest.getApprovals().remove(approvals.size()-1);
                            
                        } else {
                            return "Invalid status";
                        }
                    }else{
                        return "Your not allowed to update status2";
                    }
                    break;
                case "ceo":
                    if(approver.getId()==approvalRequest.getCeoApprover().getId())
                    {
                        if (status.equalsIgnoreCase("approve")) {
                            approvalRequest.setCeoStatus("Approved");
                            approvals.add(approval);
                            approvalRequest.setApprovals(approvals);
                        } else if (status.equalsIgnoreCase("reject")) {
                            approvalRequest.setCeoStatus("Rejected");
                            approvalRequest.setManagerStatus("Pending");
                            approvalRequest.getApprovals().remove(approvals.size()-1);
                        } else {
                            return "Invalid status";
                        }
                    }else{
                        return "Your not allowed to update status3";
                    }
                    break;
            }
            boolean allStagesApproved = true;
            for (String stage : workflow.getStages()) {
                if ((stage.equals("lead") && !approvalRequest.getLeadStatus().equals("Approved")) ||
                    (stage.equals("manager") && !approvalRequest.getManagerStatus().equals("Approved")) ||
                    (stage.equals("ceo") && !approvalRequest.getCeoStatus().equals("Approved"))) {
                    allStagesApproved = false;
                    break;
                }
            }

            if (allStagesApproved) {
                approvalRequest.setStatus("Approved");
               
            }
            approvalRequestRepository.save(approvalRequest);
        } else {
            return "Approver is not in the correct stage, Previous stage is not completed";
        }
        return "Approver review is updated";
    }

    public ApprovalRequest findById(Long id) {
        return approvalRequestRepository.findById(id).orElse(null);
    }

    public List<ApprovalRequest> getApprovalRequestsForApprover(Long approverId) {
        return approvalRequestRepository.findByApprovalsApproverIdAndStatus(approverId, "Pending");
    }
}