package com.example.application_management_system.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    @OneToMany
    @JoinColumn(name= "applicant_Id", nullable = false)
    @JsonBackReference
    private Applicant applicant;
}

