package com.example.workhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "workstations") // tabela no plural
public class Workstations {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;      // ex.: "Mesa 12"

    private String location;  // ex.: "3º andar"

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
}
