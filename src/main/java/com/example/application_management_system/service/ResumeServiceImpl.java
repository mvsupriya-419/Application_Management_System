package com.example.application_management_system.service;

import com.example.application_management_system.Entity.Applicant;
import com.example.application_management_system.Entity.Resume;
import com.example.application_management_system.repositories.ApplicantRepository;
import com.example.application_management_system.repositories.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService{

        @Autowired
        private ResumeRepository resumeRepository;

        @Autowired
        private ApplicantRepository applicantRepository;


    @Override
    public Resume addResume(Resume resume) {

        Applicant applicant = applicantRepository.findById(resume.getApplicant().getId())
                .orElseThrow(() -> new RuntimeException("Applicant not found"));

        resume.setApplicant(applicant);
        applicant.setResume(resume);

        return resumeRepository.save(resume);
    }



}
