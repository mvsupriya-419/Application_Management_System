package com.example.application_management_system.controllers;

import com.example.application_management_system.Entity.Resume;
import com.example.application_management_system.service.ResumeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ResumeController {

    @Autowired
    private ResumeServiceImpl resumeServiceImpl;

    @PostMapping("/resume")
    public ResponseEntity<Resume> addResume(@RequestBody Resume resume){
        return ResponseEntity.ok(resumeServiceImpl.addResume(resume));
    }
}
