package com.example.workhub.controller;

import com.example.workhub.model.Users;
import com.example.workhub.repo.UsersRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsersRepo usersRepo;

    public AuthController(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (req.getName() == null || req.getName().isBlank()
                || req.getEmail() == null || req.getEmail().isBlank()
                || req.getPassword() == null || req.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("name, email e password são obrigatórios");
        }

        String email = req.getEmail().trim().toLowerCase();
        if (usersRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body("Email já cadastrado");
        }

        Users u = new Users();
        u.setName(req.getName().trim());
        u.setEmail(email);
        u.setPassword(req.getPassword()); // SEM hash — apenas DEV

        Users saved = usersRepo.save(u);

        return ResponseEntity.ok(Map.of(
                "id", saved.getId(),
                "name", saved.getName(),
                "email", saved.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String email = req.getEmail() == null ? "" : req.getEmail().trim().toLowerCase();
        Optional<Users> opt = usersRepo.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
        Users u = opt.get();

        String raw = req.getPassword() == null ? "" : req.getPassword();
        if (!raw.equals(u.getPassword())) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        // Token fake (apenas demo). Em produção, use JWT.
        String token = UUID.randomUUID().toString();

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", Map.of(
                        "id", u.getId(),
                        "name", u.getName(),
                        "email", u.getEmail()
                )
        ));
    }

    // DTOs sem Lombok
    public static class SignupRequest {
        private String name;
        private String email;
        private String password;

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
    }
}
