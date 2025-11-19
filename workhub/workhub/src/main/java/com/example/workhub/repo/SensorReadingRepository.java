package com.example.workhub.repo;

import com.example.workhub.model.SensorReading;
import com.example.workhub.model.Workstations;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    Optional<SensorReading> findTopByWorkstationOrderByTimestampDesc(Workstations workstation);
}
