package com.example.workhub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservations {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(optional = false) @JoinColumn(name = "workstation_id", nullable = false)
    private Workstations workstation;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean cancelled = false;

    // Getters
    public Long getId() { return id; }
    public Users getUser() { return user; }
    public Workstations getWorkstation() { return workstation; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isCancelled() { return cancelled; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUser(Users user) { this.user = user; }
    public void setWorkstation(Workstations workstation) { this.workstation = workstation; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}
