package com.example.TimesheetApproval.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
 
    private String role;  // Role is now a String field
 
    @ManyToOne
    @JoinColumn(name = "leadId", referencedColumnName = "id")
    private User lead;
 
    @ManyToOne
    @JoinColumn(name = "managerId", referencedColumnName = "id")
    private User manager;
 
    @ManyToOne
    @JoinColumn(name = "ceoId", referencedColumnName = "id")
    private User ceo;
 

}