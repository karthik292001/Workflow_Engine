package com.example.TimesheetApproval.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TimesheetApproval.entities.User;
import com.example.TimesheetApproval.entities.Workflow;
import com.example.TimesheetApproval.services.WorkflowService;






@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;


   
    @PostMapping("/create")
    public Workflow createWorkflow(@RequestBody Workflow workflow) {
        
        return workflowService.createWorkflow(workflow);
    }


    @GetMapping
    public List<Workflow> getAllWorkflows() {
        return workflowService.getAllWorkflows();
    }


    @GetMapping("/{id}")
    public Workflow getWorkflowById(@PathVariable Long id) {
        return workflowService.getWorkflow(id);
    }


    @GetMapping("/{id}/approvers")
    public List<User> getApproversForWorkflow(@PathVariable Long id) {
        return workflowService.getApproversForWorkflow(id);
    }
}


