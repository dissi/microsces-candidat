package com.example.microsces.candidat.domaine.service;

import com.example.microsces.candidat.domaine.model.Cv;
import com.example.microsces.candidat.domaine.port.in.CvFileUseCase;
import com.example.microsces.candidat.domaine.port.out.CvRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class CvFileService implements CvFileUseCase {
    private final CvRepositoryPort cvRepositoryPort;


    @Override
    public void uploadCv(Cv cv) {
        cvRepositoryPort.uploadCv(cv);
    }

    /**
     * Récupère un cv d'un candidat par son ID.
     *
     * @param candidatId L'ID du candidat
     * @return Le CV du candidat
     */
    @Override
    public Cv downloadCv(UUID candidatId) {
        return cvRepositoryPort.downloadCv(candidatId);
    }


}
