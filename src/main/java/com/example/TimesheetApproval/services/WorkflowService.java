package com.example.TimesheetApproval.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TimesheetApproval.entities.User;
import com.example.TimesheetApproval.entities.Workflow;
import com.example.TimesheetApproval.repositories.UserRepository;
import com.example.TimesheetApproval.repositories.WorkflowRepository;


@Service
public class WorkflowService {
    @Autowired
    private WorkflowRepository workflowRepository;
    @Autowired
    private UserRepository userRepository;

    public Workflow getWorkflow(Long id) {
        return workflowRepository.findById(id).orElse(null);
    }

    public List<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }

    public Workflow createWorkflow(Workflow workflow) {
        return workflowRepository.save(workflow);
    }

    public List<User> getApproversForWorkflow(Long workflowId) {
        Workflow workflow = getWorkflow(workflowId);
        if (workflow == null) {
            return List.of();
        }

        return workflow.getStages().stream()
                .map(role -> userRepository.findByRole(role))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}




