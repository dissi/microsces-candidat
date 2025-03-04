package com.example.microsces.candidat.domaine.model;

import static org.junit.jupiter.api.Assertions.*;

import com.example.microsces.candidat.domaine.exception.CandidatValidationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class CandidatTest {

    // Test de la création d'un candidat avec des informations valides
    @Test
    void testCreateCandidat_ValidData() {
        UUID id = UUID.randomUUID();
        String nom = "John Doe";
        String email = "johndoe@example.com";
        String telephone = "0123456789";

        Candidat candidat = new Candidat(id, nom, email, telephone);

        assertNotNull(candidat);
        assertEquals(id, candidat.getId());
        assertEquals(nom, candidat.getNom());
        assertEquals(email, candidat.getEmail());
        assertEquals(telephone, candidat.getTelephone());
    }

    // Test de la création avec un nom vide
    @Test
    void testCreateCandidat_EmptyNom() {
        UUID id = UUID.randomUUID();
        String nom = "";
        String email = "johndoe@example.com";
        String telephone = "0123456789";

        CandidatValidationException exception = assertThrows(CandidatValidationException.class, () -> {
            new Candidat(id, nom, email, telephone);
        });

        assertEquals("Le nom ne peut pas être vide.", exception.getMessage());
    }

    // Test de la création avec un nom null
    @Test
    void testCreateCandidat_NullNom() {
        UUID id = UUID.randomUUID();
        String nom = null;
        String email = "johndoe@example.com";
        String telephone = "0123456789";

        CandidatValidationException exception = assertThrows(CandidatValidationException.class, () -> {
            new Candidat(id, nom, email, telephone);
        });

        assertEquals("Le nom ne peut pas être vide.", exception.getMessage());
    }

    // Test de la création avec un email invalide
    @Test
    void testCreateCandidat_InvalidEmail() {
        UUID id = UUID.randomUUID();
        String nom = "John Doe";
        String email = "invalid-email";
        String telephone = "0123456789";

        CandidatValidationException exception = assertThrows(CandidatValidationException.class, () -> {
            new Candidat(id, nom, email, telephone);
        });

        assertEquals("Email invalide.", exception.getMessage());
    }

    // Test de la création avec un téléphone invalide (moins de 10 chiffres)
    @Test
    void testCreateCandidat_InvalidTelephone() {
        UUID id = UUID.randomUUID();
        String nom = "John Doe";
        String email = "johndoe@example.com";
        String telephone = "012345678";

        CandidatValidationException exception = assertThrows(CandidatValidationException.class, () -> {
            new Candidat(id, nom, email, telephone);
        });

        assertEquals("Le numéro de téléphone doit contenir exactement 10 chiffres.", exception.getMessage());
    }

    // Test de la création avec un téléphone invalide (plus de 10 chiffres)
    @Test
    void testCreateCandidat_TelephoneTooLong() {
        UUID id = UUID.randomUUID();
        String nom = "John Doe";
        String email = "johndoe@example.com";
        String telephone = "01234567890"; // 11 chiffres

        CandidatValidationException exception = assertThrows(CandidatValidationException.class, () -> {
            new Candidat(id, nom, email, telephone);
        });

        assertEquals("Le numéro de téléphone doit contenir exactement 10 chiffres.", exception.getMessage());
    }

    // Test de la création avec un téléphone null
    @Test
    void testCreateCandidat_NullTelephone() {
        UUID id = UUID.randomUUID();
        String nom = "John Doe";
        String email = "johndoe@example.com";
        String telephone = null;

        CandidatValidationException exception = assertThrows(CandidatValidationException.class, () -> {
            new Candidat(id, nom, email, telephone);
        });

        assertEquals("Le numéro de téléphone doit contenir exactement 10 chiffres.", exception.getMessage());
    }

    // Test de la création avec un email vide
    @Test
    void testCreateCandidat_EmptyEmail() {
        UUID id = UUID.randomUUID();
        String nom = "John Doe";
        String email = "";
        String telephone = "0123456789";

        CandidatValidationException exception = assertThrows(CandidatValidationException.class, () -> {
            new Candidat(id, nom, email, telephone);
        });

        assertEquals("Email invalide.", exception.getMessage());
    }
}
