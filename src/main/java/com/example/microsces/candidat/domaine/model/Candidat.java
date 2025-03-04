package com.example.microsces.candidat.domaine.model;

import com.example.microsces.candidat.domaine.exception.CandidatValidationException;

import java.util.UUID;


/**
 * Représente un candidat avec ses informations de base.
 */

public class Candidat {
    private UUID id;
    private String nom;
    private String email;
    private String telephone;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Constructeur pour créer un candidat.
     *
     * @param id        Identifiant unique du candidat
     * @param nom       Nom du candidat
     * @param email     Adresse email du candidat
     * @param telephone Numéro de téléphone du candidat (10 chiffres)
     */
    public Candidat(UUID id, String nom, String email, String telephone) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new CandidatValidationException("Le nom ne peut pas être vide.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") ) {
            throw new CandidatValidationException("Email invalide.");
        }
        if (telephone == null || !telephone.matches("^[0-9]{10}$")) {
            throw new CandidatValidationException("Le numéro de téléphone doit contenir exactement 10 chiffres.");
        }

        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    /**
     * Retourne l'identifiant du candidat.
     *
     * @return UUID du candidat
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retourne le nom du candidat.
     *
     * @return Nom du candidat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne l'email du candidat.
     *
     * @return Email du candidat
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le numéro de téléphone du candidat.
     *
     * @return Téléphone du candidat (10 chiffres)
     */
    public String getTelephone() {
        return telephone;
    }

}
