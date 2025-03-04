package com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres;

import com.example.microsces.candidat.domaine.model.Candidat;
import com.example.microsces.candidat.domaine.port.out.CandidatRepositoryPort;
import com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres.mapper.CandidatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CandidatRepositoryPortAdapter implements CandidatRepositoryPort {
    private final CandidatJpaRepository candidatJpaRepository;
    private final CandidatMapper candidatMapper;
    @Override
    public Candidat save(Candidat candidat) {
        CandidatEntity candidatEntity = candidatMapper.domainToEntity(candidat);
        CandidatEntity candidatEntitySaved = candidatJpaRepository.save(candidatEntity);
        return candidatMapper.entityToDomain(candidatEntitySaved);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Candidat> findById(UUID id) {
        return candidatJpaRepository.findById(id).map(candidatMapper::entityToDomain);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Candidat> findAll(Pageable pageable) {
        return candidatJpaRepository.findAll(pageable).map(candidatMapper::entityToDomain);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(UUID id) {
        CandidatEntity candidatEntity = candidatJpaRepository.findById(id).orElse(null);
        if (candidatEntity != null) {
            candidatJpaRepository.delete(candidatEntity);
        }
    }


}
