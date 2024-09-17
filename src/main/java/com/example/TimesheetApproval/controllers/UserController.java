package com.example.TimesheetApproval.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TimesheetApproval.entities.User;
import com.example.TimesheetApproval.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
 
    @Autowired
    private UserService employeeService;
 
    @PostMapping("/create")
    public User createEmployee(@RequestBody User user) {
        
        return employeeService.createEmployee(user);
    }
 
}