package com.example.application_management_system.service;

import com.example.application_management_system.Entity.Applicant;

import java.util.List;

public interface ApplicantService {
   Applicant addApplicant(Applicant applicant);

   Applicant findByName(String name);

   Applicant updateApplicant(Applicant applicant);

    void deleteApplicant(Long id);

    List<Applicant> getAllApplicants();
}
