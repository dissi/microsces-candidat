package com.example.microsces.candidat.domaine.model;

import com.example.microsces.candidat.domaine.exception.CvValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CvTest {

    // Test de la création d'un CV avec un fichier valide
    @Test
    void testCreateFromMultipartFile_ValidFile() throws IOException, CvValidationException {
        UUID candidatId = UUID.randomUUID();
        byte[] fileContent = new byte[100]; // Fichier de test
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn((long) fileContent.length);
        when(file.getBytes()).thenReturn(fileContent);
        when(file.getContentType()).thenReturn("application/pdf");

        Cv cv = Cv.createFromMultipartFile(candidatId, file);

        assertNotNull(cv);
        assertEquals(candidatId, cv.getCandidatId());
        assertArrayEquals(fileContent, cv.getCvData());
    }

    // Test de la validation du fichier vide
    @Test
    void testCreateFromMultipartFile_EmptyFile() {
        UUID candidatId = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(true);

        CvValidationException exception = assertThrows(CvValidationException.class, () -> {
            Cv.createFromMultipartFile(candidatId, file);
        });

        assertEquals("Le fichier est vide", exception.getMessage());
    }

    // Test de la validation du fichier trop volumineux
    @Test
    void testCreateFromMultipartFile_FileTooLarge() {
        UUID candidatId = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(6 * 1024 * 1024L); // 6 MB
        when(file.getContentType()).thenReturn("application/pdf");

        CvValidationException exception = assertThrows(CvValidationException.class, () -> {
            Cv.createFromMultipartFile(candidatId, file);
        });

        assertEquals("Le fichier dépasse la taille maximale autorisée (5 MB)", exception.getMessage());
    }

    // Test de la validation du type de fichier non autorisé
    @Test
    void testCreateFromMultipartFile_InvalidFileType() {
        UUID candidatId = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(100L);
        when(file.getContentType()).thenReturn("image/png");

        CvValidationException exception = assertThrows(CvValidationException.class, () -> {
            Cv.createFromMultipartFile(candidatId, file);
        });

        assertEquals("Type de fichier non autorisé. Seuls les fichiers PDF, JPEG et PNG sont autorisés", exception.getMessage());
    }

    // Test de la validation de la taille d'un fichier PDF valide
    @Test
    void testCreateFromMultipartFile_ValidPdfFile() throws IOException, CvValidationException {
        UUID candidatId = UUID.randomUUID();
        byte[] fileContent = new byte[100]; // Fichier de test
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn((long) fileContent.length);
        when(file.getBytes()).thenReturn(fileContent);
        when(file.getContentType()).thenReturn("application/pdf");

        Cv cv = Cv.createFromMultipartFile(candidatId, file);

        assertNotNull(cv);
        assertEquals(candidatId, cv.getCandidatId());
        assertArrayEquals(fileContent, cv.getCvData());
    }

    // Test de l'exception si le fichier est null
    @Test
    void testCreateFromMultipartFile_NullFile() {
        UUID candidatId = UUID.randomUUID();

        CvValidationException exception = assertThrows(CvValidationException.class, () -> {
            Cv.createFromMultipartFile(candidatId, null);
        });

        assertEquals("Le fichier est vide", exception.getMessage());
    }
}