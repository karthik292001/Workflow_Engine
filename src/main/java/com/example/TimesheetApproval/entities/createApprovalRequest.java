package com.example.TimesheetApproval.entities;

import java.util.Set;

import lombok.Data;

@Data
public class createApprovalRequest {
    private Long employeeId;
    private int stages;
    private Set<FieldsValues> fields;
   
}
