package com.example.workhub.controller;

import com.example.workhub.model.Reservations;
import com.example.workhub.model.Users;
import com.example.workhub.model.Workstations;
import com.example.workhub.repo.ReservationRepository;
import com.example.workhub.repo.UsersRepo;
import com.example.workhub.repo.WorkstationsRepository;
import com.example.workhub.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationService service;
    private final UsersRepo usersRepo;
    private final WorkstationsRepository workstationRepo;
    private final ReservationRepository reservationRepo;

    public ReservationsController(ReservationService service,
                                  UsersRepo usersRepo,
                                  WorkstationsRepository workstationRepo,
                                  ReservationRepository reservationRepo) {
        this.service = service;
        this.usersRepo = usersRepo;
        this.workstationRepo = workstationRepo;
        this.reservationRepo = reservationRepo;
    }

    @GetMapping
    public List<Reservations> list() { return service.listAll(); }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateReq req) {
        if (req.userId == null || req.workstationId == null || req.start == null || req.end == null) {
            return ResponseEntity.badRequest().body("userId, workstationId, start e end são obrigatórios");
        }
        Optional<Users> u = usersRepo.findById(req.userId);
        Optional<Workstations> ws = workstationRepo.findById(req.workstationId);
        if (u.isEmpty() || ws.isEmpty()) return ResponseEntity.badRequest().body("userId/workstationId inválido");
        if (!req.end.isAfter(req.start)) return ResponseEntity.badRequest().body("end deve ser > start");
        if (service.hasConflict(ws.get(), req.start, req.end)) {
            return ResponseEntity.status(409).body("Horário indisponível para esta estação");
        }
        Reservations saved = service.create(u.get(), ws.get(), req.start, req.end);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateReq req) {
        Optional<Reservations> rOpt = reservationRepo.findById(id);
        if (rOpt.isEmpty()) return ResponseEntity.notFound().build();
        Reservations r = rOpt.get();

        if (req.start == null || req.end == null) return ResponseEntity.badRequest().body("start e end obrigatórios");
        if (!req.end.isAfter(req.start)) return ResponseEntity.badRequest().body("end deve ser > start");
        if (service.hasConflict(r.getWorkstation(), req.start, req.end)) {
            return ResponseEntity.status(409).body("Horário indisponível para esta estação");
        }
        return ResponseEntity.ok(service.updateTime(r, req.start, req.end));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        Optional<Reservations> rOpt = reservationRepo.findById(id);
        if (rOpt.isEmpty()) return ResponseEntity.notFound().build();
        service.cancel(rOpt.get());
        return ResponseEntity.ok().build();
    }

    // DTOs
    public static class CreateReq {
        public Long userId;
        public Long workstationId;
        public LocalDateTime start;
        public LocalDateTime end;
    }
    public static class UpdateReq {
        public LocalDateTime start;
        public LocalDateTime end;
    }
}
