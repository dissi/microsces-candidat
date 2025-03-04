package com.example.microsces.candidat.domaine.port.out;

import com.example.microsces.candidat.domaine.model.Candidat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Port pour l'accès aux données des candidats.
 */
public interface CandidatRepositoryPort {
    /**
     * Sauvegarde un candidat.
     *
     * @param candidat Le candidat à sauvegarder
     * @return Le candidat sauvegardé
     */
    Candidat save(Candidat candidat);

    /**
     * Recherche un candidat par son ID.
     *
     * @param id L'ID du candidat
     * @return Le candidat s'il existe, sinon un Optional vide
     */
    Optional<Candidat> findById(UUID id);

    /**
     * Récupère la liste paginée des candidats.
     *
     * @param pageable Informations de pagination
     * @return Une page contenant les candidats
     */
    Page<Candidat> findAll(Pageable pageable);

    /**
     * Supprime un candidat par son ID.
     *
     * @param id L'ID du candidat à supprimer
     */
    void deleteById(UUID id);
}
