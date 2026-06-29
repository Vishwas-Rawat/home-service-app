package com.home.model.admin;

import com.home.model.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_audit_logs", schema = "admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_audit_id")
    private Integer adminAuditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "target", nullable = false)
    private String target;

    @Column(name = "details")
    private String details;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
