package com.example.workhub.service;

import com.example.workhub.model.Workstations;
import com.example.workhub.repo.WorkstationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkstationService {

    private final WorkstationsRepository repo;

    public WorkstationService(WorkstationsRepository repo) {
        this.repo = repo;
    }

    public List<Workstations> listAll() {
        return repo.findAll();
    }
}
