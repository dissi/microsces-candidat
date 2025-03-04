package com.example.microsces.candidat.infrastucture.exception;

import com.example.microsces.candidat.domaine.exception.CandidatNotFoundException;
import com.example.microsces.candidat.domaine.exception.CandidatValidationException;
import com.example.microsces.candidat.domaine.exception.CvNotFoundException;
import com.example.microsces.candidat.domaine.exception.CvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Gestionnaire global des exceptions pour l'API des candidats.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Gère les exceptions de validation des candidats.
     *
     * @param ex L'exception de validation capturée
     * @return Une réponse HTTP avec un message d'erreur et le code approprié sous forme de ProblemDetail
     */
    @ExceptionHandler(CandidatValidationException.class)
    public ProblemDetail handleCandidatValidationException(CandidatValidationException ex) {
        LOGGER.error("Validation error: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Erreur de validation");
        return problemDetail;
    }

    /**
     * Gère les exceptions du Cv non trouvé.
     *
     * @param ex L'exception du Cv non trouvé capturée
     * @return Une réponse HTTP avec un message d'erreur et le code approprié sous forme de ProblemDetail
     */
    @ExceptionHandler(CandidatNotFoundException.class)
    public ProblemDetail handleCandidatNotFoundException(CandidatNotFoundException ex) {
        LOGGER.error("Candidat not found error: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not Found");
        return problemDetail;
    }


    /**
     * Gère les exceptions de validation du Cv.
     *
     * @param ex L'exception de validation capturée
     * @return Une réponse HTTP avec un message d'erreur et le code approprié sous forme de ProblemDetail
     */
    @ExceptionHandler(CvValidationException.class)
    public ProblemDetail handleCandidatValidationException(CvValidationException ex) {
        LOGGER.error("Validation error: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Erreur de validation");
        return problemDetail;
    }

    /**
     * Gère les exceptions du Cv non trouvé.
     *
     * @param ex L'exception du Cv non trouvé capturée
     * @return Une réponse HTTP avec un message d'erreur et le code approprié sous forme de ProblemDetail
     */
    @ExceptionHandler(CvNotFoundException.class)
    public ProblemDetail handleCvNotFoundException(CvNotFoundException ex) {
        LOGGER.error("Cv not found error: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not Found");
        return problemDetail;
    }



    /**
     * Gère les exceptions génériques non prévues.
     *
     * @param ex L'exception capturée
     * @return Une réponse HTTP avec un message d'erreur générique et un code 500 sous forme de ProblemDetail
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        LOGGER.error("Unexpected error: {}", ex.getMessage(), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue.");
        problemDetail.setTitle("Erreur interne");
        return problemDetail;
    }
}

