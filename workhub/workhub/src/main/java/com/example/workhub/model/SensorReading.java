package com.example.workhub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "workstation_id", nullable = false)
    private Workstations workstation;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean occupied;

    private Double temperatureC;
    private Integer noiseDb;

    public Long getId() { return id; }
    public Workstations getWorkstation() { return workstation; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isOccupied() { return occupied; }       // <--- garante o getter
    public Double getTemperatureC() { return temperatureC; }
    public Integer getNoiseDb() { return noiseDb; }

    public void setId(Long id) { this.id = id; }
    public void setWorkstation(Workstations workstation) { this.workstation = workstation; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
    public void setTemperatureC(Double temperatureC) { this.temperatureC = temperatureC; }
    public void setNoiseDb(Integer noiseDb) { this.noiseDb = noiseDb; }
}
