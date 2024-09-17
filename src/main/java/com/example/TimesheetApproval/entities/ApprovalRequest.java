package com.example.TimesheetApproval.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ApprovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User employee;

    @ManyToOne
    private Workflow workflow;

    private List<String> stages;

    @OneToMany(mappedBy = "approvalRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Approval> approvals;

    private String status; // e.g., "Pending", "Approved"
    private String leadStatus; // e.g., "Pending", "Approved"
    private String managerStatus; // e.g., "Pending", "Approved"
    private String ceoStatus; // e.g., "Pending", "Approved"

    @OneToMany(mappedBy = "approvalRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FieldsValues> fieldsValues = new HashSet<>();

    @ManyToOne
    private User leadApprover; // New field for lead approver

    @ManyToOne
    private User managerApprover; // New field for manager approver

    @ManyToOne
    private User ceoApprover; 

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApprovalRequest that = (ApprovalRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
