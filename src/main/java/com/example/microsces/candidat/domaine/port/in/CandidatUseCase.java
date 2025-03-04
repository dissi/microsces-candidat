package com.example.microsces.candidat.domaine.port.in;

import com.example.microsces.candidat.domaine.model.Candidat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Port pour la gestion métier des candidats.
 */
public interface CandidatUseCase {
    /**
     * Crée un nouveau candidat.
     *
     * @param candidat Le candidat à créer
     * @return Le candidat créé
     */
    Candidat createCandidat(Candidat candidat);

    /**
     * Récupère un candidat par son ID.
     *
     * @param id L'ID du candidat
     * @return Le candidat s'il existe
     */
    Candidat getCandidatById(UUID id);

    /**
     * Récupère la liste paginée des candidats.
     *
     * @param pageable Informations de pagination
     * @return Une page contenant les candidats
     */
    Page<Candidat> getCandidats(Pageable pageable);

    /**
     * Met à jour un candidat existant.
     *
     * @param id L'ID du candidat
     * @param candidat Les nouvelles informations du candidat
     * @return Le candidat mis à jour
     */
    Candidat updateCandidat(UUID id, Candidat candidat);

    /**
     * Supprime un candidat par son ID.
     *
     * @param id L'ID du candidat à supprimer
     */
    void deleteCandidat(UUID id);
}
