package com.example.microsces.candidat.domaine.service;


import com.example.microsces.candidat.domaine.exception.CandidatNotFoundException;
import com.example.microsces.candidat.domaine.model.Candidat;
import com.example.microsces.candidat.domaine.port.in.CandidatUseCase;
import com.example.microsces.candidat.domaine.port.out.CandidatRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class CandidatService implements CandidatUseCase {
    private final CandidatRepositoryPort candidatRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Candidat> getCandidats(Pageable pageable) {
       return candidatRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Candidat createCandidat(Candidat candidat) {
        return candidatRepository.save(candidat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Candidat getCandidatById(UUID id) {
        return candidatRepository.findById(id).orElseThrow(()->new CandidatNotFoundException("Candidat not found"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Candidat updateCandidat(UUID id, Candidat request) {
        Candidat existingCandidat = getCandidatById(id);
        Optional.ofNullable(request.getNom()).ifPresent(existingCandidat::setNom);
        Optional.ofNullable(request.getEmail()).ifPresent(existingCandidat::setEmail);
        Optional.ofNullable(request.getTelephone()).ifPresent(existingCandidat::setTelephone);

        return candidatRepository.save(existingCandidat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCandidat(UUID id) {
    Candidat candidat = getCandidatById(id);
    candidatRepository.deleteById(candidat.getId());
    }
}
