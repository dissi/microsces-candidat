package com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres;

import com.example.microsces.candidat.domaine.exception.CandidatNotFoundException;
import com.example.microsces.candidat.domaine.exception.CvNotFoundException;
import com.example.microsces.candidat.domaine.model.Cv;
import com.example.microsces.candidat.domaine.port.out.CvRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CvRepositoryPortAdapter implements CvRepositoryPort {
    private final CandidatJpaRepository candidatJpaRepository;

    /**
     * Sauvegarde le cv du  candidat.
     *
     * @param cv Le cv candidat à sauvegarder
     */
    @Override
    public void uploadCv(Cv cv) {
        CandidatEntity candidatEntity = candidatJpaRepository.findById(cv.getCandidatId()).orElseThrow(()->new CandidatNotFoundException(String.format("Candidat not found %s",cv.getCandidatId())));

        candidatJpaRepository.save(candidatEntity);
    }

    /**
     * Recupere le Cv du  candidat par son ID.
     *
     * @param candidatId L'ID du candidat
     * @return Le CV s'il existe
     */
    @Override
    public Cv downloadCv(UUID candidatId) {
        CandidatEntity candidatEntity = candidatJpaRepository.findById(candidatId).orElseThrow(()->new CandidatNotFoundException(String.format("Candidat not found %s",candidatId)));
        if(candidatEntity.getCvData()==null){
            throw new CvNotFoundException("Aucun CV trouvé pour ce candidat");
        }
        return new Cv(candidatEntity.getId(), candidatEntity.getCvData());
    }
}
