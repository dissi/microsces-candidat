package com.example.microsces.candidat.domaine.service;

import com.example.microsces.candidat.domaine.exception.CandidatNotFoundException;
import com.example.microsces.candidat.domaine.model.Cv;
import com.example.microsces.candidat.domaine.port.out.CvRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CvFileServiceTest {

    @Mock
    private CvRepositoryPort cvRepositoryPort;

    @InjectMocks
    private CvFileService cvFileService;

    // Test de la méthode uploadCv
    @Test
    void testUploadCv() {
        // Given
        UUID candidatId = UUID.randomUUID();
        byte[] cvData = new byte[]{1, 2, 3, 4};  // Simuler des données de CV
        Cv cv = new Cv(candidatId, cvData);

        doNothing().when(cvRepositoryPort).uploadCv(cv);

        // When
        cvFileService.uploadCv(cv);

        // Then
        verify(cvRepositoryPort).uploadCv(cv);
    }

    // Test de la méthode downloadCv - Candidat non trouvé
    @Test
    void testDownloadCv_CandidatNotFound() {
        // Given
        UUID candidatId = UUID.randomUUID();

        when(cvRepositoryPort.downloadCv(candidatId)).thenThrow(new CandidatNotFoundException("Candidat not found"));

        // When & Then
        CandidatNotFoundException exception = assertThrows(CandidatNotFoundException.class, () -> {
            cvFileService.downloadCv(candidatId);
        });

        assertEquals("Candidat not found", exception.getMessage());
    }

    // Test de la méthode downloadCv - Succès
    @Test
    void testDownloadCv_Success() {
        // Given
        UUID candidatId = UUID.randomUUID();
        byte[] cvData = new byte[]{1, 2, 3, 4};  // Simuler des données de CV
        Cv expectedCv = new Cv(candidatId, cvData);

        when(cvRepositoryPort.downloadCv(candidatId)).thenReturn(expectedCv);

        // When
        Cv result = cvFileService.downloadCv(candidatId);

        // Then
        assertNotNull(result);
        assertEquals(candidatId, result.getCandidatId());
        assertArrayEquals(cvData, result.getCvData());
    }
}
