package com.example.TimesheetApproval.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TimesheetApproval.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(String role);
}
