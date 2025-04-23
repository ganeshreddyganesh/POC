package com.mobily.loyalty.service.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username; // User Identifier
    @Column(nullable = false)
    private String action; // Action Taken
    @Column(nullable = false)
    private Date timestamp; // Timestamp of the Action
    @Column(nullable = false)
    private String resource; // Resource Accessed
    @Column
    private String details; // Additional Details

    // Standard getters and setters
}
