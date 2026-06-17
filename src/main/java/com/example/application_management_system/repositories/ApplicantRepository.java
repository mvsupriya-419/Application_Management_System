package com.example.application_management_system.repositories;

import com.example.application_management_system.Entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant ,Long> {

    Applicant findByName(String name);
}
