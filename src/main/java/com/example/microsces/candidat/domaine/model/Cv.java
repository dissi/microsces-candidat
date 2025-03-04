package com.example.microsces.candidat.domaine.model;

import com.example.microsces.candidat.domaine.exception.CvValidationException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Représente un CV d'un candidat, incluant les données du fichier et son identifiant associé.
 */
@Getter
public class Cv {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB max
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("application/pdf");

    private UUID candidatId;
    private byte[] cvData;

    /**
     * Constructeur privé pour créer un objet Cv à partir des données du fichier.
     *
     * @param candidatId L'identifiant du candidat auquel ce CV est associé.
     * @param cvData Les données du CV en tableau de bytes.
     */
    public Cv(UUID candidatId, byte[] cvData) {
        this.candidatId = candidatId;
        this.cvData = cvData;
    }

    /**
     * Crée un objet Cv à partir d'un fichier multipart et valide le fichier.
     *
     * @param candidatId L'identifiant du candidat auquel ce CV est associé.
     * @param file Le fichier MultipartFile représentant le CV.
     * @return Un objet Cv contenant les données du fichier.
     * @throws CvValidationException Si le fichier ne respecte pas les critères de validation.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    public static Cv createFromMultipartFile(UUID candidatId, MultipartFile file) throws CvValidationException, IOException {

        validateFile(file);

        byte[] cvData = file.getBytes();
        return new Cv(candidatId, cvData);
    }

    /**
     * Valide le fichier avant de le convertir en données de CV.
     *
     * @param file Le fichier à valider.
     * @throws CvValidationException Si le fichier est invalide.
     */
    private static void validateFile(MultipartFile file) throws CvValidationException {

        if (file==null || file.isEmpty()) {
            throw new CvValidationException("Le fichier est vide");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new CvValidationException("Le fichier dépasse la taille maximale autorisée (5 MB)");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new CvValidationException("Type de fichier non autorisé. Seuls les fichiers PDF sont autorisés");
        }
    }
}

