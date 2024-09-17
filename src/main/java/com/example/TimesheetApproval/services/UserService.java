package com.example.TimesheetApproval.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TimesheetApproval.entities.User;
import com.example.TimesheetApproval.repositories.UserRepository;

@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;
 
   
    public User createEmployee(User user) {
        
 
        if ("ENGINEER".equalsIgnoreCase(user.getRole())) {
            User lead = userRepository.findById(user.getLead().getId()).orElseThrow(() -> new IllegalArgumentException("Lead not found"));
            user.setLead(lead);
        } else if ("LEAD".equalsIgnoreCase(user.getRole())) {
            User manager = userRepository.findById(user.getManager().getId()).orElseThrow(() -> new IllegalArgumentException("Manager not found"));
            user.setManager(manager);
        } else if ("MANAGER".equalsIgnoreCase(user.getRole())) {
            User ceo = userRepository.findById(user.getCeo().getId()).orElseThrow(() -> new IllegalArgumentException("CEO not found"));
            user.setCeo(ceo);
        }
 
        return userRepository.save(user);
    }
}
