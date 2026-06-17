package com.example.application_management_system.controllers;

import com.example.application_management_system.Entity.Applicant;
import com.example.application_management_system.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/Applicant")
public class ApplicantController {

    private final  ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Applicant> addApplicant(@RequestBody Applicant applicant){
        Applicant addApplicant = applicantService.addApplicant(applicant);
        return ResponseEntity.ok(addApplicant);
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        return ResponseEntity.ok(applicantService.getAllApplicants());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Applicant> getApplicantByName(@PathVariable String name){
        Applicant findByName = applicantService.findByName(name);
        return ResponseEntity.ok(findByName);
    }

    @PutMapping
    public ResponseEntity<Applicant > updateApplicant(@RequestBody Applicant applicant){
        Applicant updateApplicant= applicantService.updateApplicant(applicant);
        return ResponseEntity.ok(updateApplicant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long id) {
        applicantService.deleteApplicant(id);
        return ResponseEntity.ok().build();
    }
}
