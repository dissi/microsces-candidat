package com.example.microsces.candidat.domaine.exception;

public class CandidatNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public CandidatNotFoundException(String message) {
        super(message);
    }
}
