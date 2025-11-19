package com.example.workhub.service;

import com.example.workhub.model.SensorReading;
import com.example.workhub.model.Workstations;
import com.example.workhub.repo.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorReadingRepository repo;

    public SensorService(SensorReadingRepository repo) {
        this.repo = repo;
    }

    public SensorReading add(Workstations ws, Boolean occupied, Double tempC, Integer noiseDb, LocalDateTime ts) {
        SensorReading sr = new SensorReading();
        sr.setWorkstation(ws);
        sr.setOccupied(Boolean.TRUE.equals(occupied));
        sr.setTemperatureC(tempC);
        sr.setNoiseDb(noiseDb);
        sr.setTimestamp(ts != null ? ts : LocalDateTime.now());
        return repo.save(sr);
    }

    public Optional<SensorReading> lastOf(Workstations ws) {
        return repo.findTopByWorkstationOrderByTimestampDesc(ws);
    }
}
