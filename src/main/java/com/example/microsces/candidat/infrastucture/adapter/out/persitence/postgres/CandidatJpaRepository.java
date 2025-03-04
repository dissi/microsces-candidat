package com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CandidatJpaRepository extends JpaRepository<CandidatEntity, UUID> {
}
