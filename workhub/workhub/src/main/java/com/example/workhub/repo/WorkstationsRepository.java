package com.example.workhub.repo;

import com.example.workhub.model.Workstations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkstationsRepository extends JpaRepository<Workstations, Long> {}
