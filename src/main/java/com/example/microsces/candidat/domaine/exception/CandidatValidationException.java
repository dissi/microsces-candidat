package com.example.microsces.candidat.domaine.exception;


/**
 * Exception personnalisée pour la validation des candidats.
 */
public class CandidatValidationException extends RuntimeException {
    public CandidatValidationException(String message) {
        super(message);
    }
}