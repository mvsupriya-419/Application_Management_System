package com.example.application_management_system.service;

import com.example.application_management_system.Entity.Applicant;
import com.example.application_management_system.repositories.ApplicantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {


    private final ApplicantRepository applicantRepository;


    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public Applicant addApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @Override
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }


    @Override
    public Applicant findByName(String name){
        return applicantRepository.findByName(name);
    }

    @Override
    public Applicant updateApplicant(Applicant applicant){
        return applicantRepository.save(applicant);
    }

    @Override
    public void deleteApplicant(Long id) {
        applicantRepository.deleteById(id);
    }

}
