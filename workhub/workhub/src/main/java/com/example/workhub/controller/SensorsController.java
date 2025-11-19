package com.example.workhub.controller;

import com.example.workhub.model.SensorReading;
import com.example.workhub.model.Workstations;
import com.example.workhub.repo.WorkstationsRepository;
import com.example.workhub.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorService sensorService;
    private final WorkstationsRepository workstationRepo;

    public SensorsController(SensorService sensorService, WorkstationsRepository workstationRepo) {
        this.sensorService = sensorService;
        this.workstationRepo = workstationRepo;
    }

    @PostMapping("/readings")
    public ResponseEntity<?> addReading(@RequestBody SensorReadingRequest req) {
        if (req.getWorkstationId() == null) return ResponseEntity.badRequest().body("workstationId é obrigatório");
        Optional<Workstations> wsOpt = workstationRepo.findById(req.getWorkstationId());
        if (wsOpt.isEmpty()) return ResponseEntity.badRequest().body("workstationId inválido");
        if (req.getOccupied() == null) return ResponseEntity.badRequest().body("occupied é obrigatório");

        SensorReading saved = sensorService.add(wsOpt.get(), req.getOccupied(), req.getTemperatureC(),
                req.getNoiseDb(), req.getTimestamp());

        return ResponseEntity.ok(Map.of(
                "id", saved.getId(),
                "workstationId", saved.getWorkstation().getId(),
                "timestamp", saved.getTimestamp(),
                "occupied", saved.isOccupied(),
                "temperatureC", saved.getTemperatureC(),
                "noiseDb", saved.getNoiseDb()
        ));
    }

    @GetMapping("/last/{workstationId}")
    public ResponseEntity<?> lastReading(@PathVariable Long workstationId) {
        Optional<Workstations> wsOpt = workstationRepo.findById(workstationId);
        if (wsOpt.isEmpty()) return ResponseEntity.badRequest().body("workstationId inválido");

        return sensorService.lastOf(wsOpt.get())
                .<ResponseEntity<?>>map(last -> ResponseEntity.ok(Map.of(
                        "id", last.getId(),
                        "workstationId", last.getWorkstation().getId(),
                        "timestamp", last.getTimestamp(),
                        "occupied", last.isOccupied(),
                        "temperatureC", last.getTemperatureC(),
                        "noiseDb", last.getNoiseDb()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // DTO
    public static class SensorReadingRequest {
        private Long workstationId;
        private Boolean occupied;
        private Double temperatureC;
        private Integer noiseDb;
        private LocalDateTime timestamp;

        public Long getWorkstationId() { return workstationId; }
        public Boolean getOccupied() { return occupied; }
        public Double getTemperatureC() { return temperatureC; }
        public Integer getNoiseDb() { return noiseDb; }
        public LocalDateTime getTimestamp() { return timestamp; }

        public void setWorkstationId(Long workstationId) { this.workstationId = workstationId; }
        public void setOccupied(Boolean occupied) { this.occupied = occupied; }
        public void setTemperatureC(Double temperatureC) { this.temperatureC = temperatureC; }
        public void setNoiseDb(Integer noiseDb) { this.noiseDb = noiseDb; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}
