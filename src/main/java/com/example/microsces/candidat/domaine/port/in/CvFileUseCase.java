package com.example.microsces.candidat.domaine.port.in;

import com.example.microsces.candidat.domaine.model.Cv;

import java.util.UUID;


/**
 * Port pour la gestion métier du cv des candidats.
 */
public interface CvFileUseCase {

    /**
     * Ajouter le cv à candidat.
     *
     * @param cv Le cv du candidat à ajouter
     *
     */
    void uploadCv(Cv cv);

    /**
     * Récupère un cv d'un candidat par son ID.
     *
     * @param candidatId L'ID du candidat
     * @return Le CV du candidat
     */
    Cv downloadCv(UUID candidatId);
}
