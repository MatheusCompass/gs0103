package com.example.workhub.repo;

import com.example.workhub.model.Reservations;
import com.example.workhub.model.Workstations;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservations, Long> {
    // verifica conflito simples
    boolean existsByWorkstationAndCancelledFalseAndStartTimeLessThanAndEndTimeGreaterThan(
            Workstations workstation, LocalDateTime end, LocalDateTime start);
}
