package com.example.TimesheetApproval.entities;





import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private List<String> stages; // e.g., ["lead", "manager", "ceo"]

    @OneToMany
    @JoinColumn(name = "workflow_id")
    private Set<Fields> fields;
}