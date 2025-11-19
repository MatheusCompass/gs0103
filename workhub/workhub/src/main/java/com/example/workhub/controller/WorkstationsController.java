package com.example.workhub.controller;

import com.example.workhub.model.Workstations;
import com.example.workhub.service.WorkstationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workstations")
public class WorkstationsController {
    private final WorkstationService service;
    public WorkstationsController(WorkstationService service) { this.service = service; }

    @GetMapping
    public List<Workstations> list() { return service.listAll(); }
}
