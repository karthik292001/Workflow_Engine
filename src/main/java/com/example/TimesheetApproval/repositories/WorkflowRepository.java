package com.example.TimesheetApproval.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TimesheetApproval.entities.Workflow;


public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    Workflow findByName(String name);
}

