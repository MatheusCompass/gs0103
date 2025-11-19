package com.example.workhub.service;

import com.example.workhub.model.Reservations;
import com.example.workhub.model.Users;
import com.example.workhub.model.Workstations;
import com.example.workhub.repo.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repo;

    public ReservationService(ReservationRepository repo) {
        this.repo = repo;
    }

    public List<Reservations> listAll() {
        return repo.findAll();
    }

    public boolean hasConflict(Workstations ws, LocalDateTime start, LocalDateTime end) {
        return repo.existsByWorkstationAndCancelledFalseAndStartTimeLessThanAndEndTimeGreaterThan(ws, end, start);
    }

    public Reservations create(Users user, Workstations ws, LocalDateTime start, LocalDateTime end) {
        Reservations r = new Reservations();
        r.setUser(user);
        r.setWorkstation(ws);
        r.setStartTime(start);
        r.setEndTime(end);
        r.setCancelled(false);
        return repo.save(r);
    }

    public Reservations updateTime(Reservations r, LocalDateTime start, LocalDateTime end) {
        r.setStartTime(start);
        r.setEndTime(end);
        return repo.save(r);
    }

    public void cancel(Reservations r) {
        r.setCancelled(true);
        repo.save(r);
    }
}
