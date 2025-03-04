package com.example.microsces.candidat.domaine.port.out;

import com.example.microsces.candidat.domaine.model.Cv;

import java.util.UUID;

/**
 * Port pour l'accès aux données du CV de candidat.
 */
public interface CvRepositoryPort {

    /**
     * Sauvegarde le cv du  candidat.
     *
     * @param cv Le cv candidat à sauvegarder
     *
     */
    void uploadCv(Cv cv);

    /**
     * Recupere le Cv du  candidat par son ID.
     *
     * @param candidatId L'ID du candidat
     * @return Le CV s'il existe
     */
    Cv downloadCv(UUID candidatId);
}
