package com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres;

import com.example.microsces.candidat.domaine.exception.CandidatNotFoundException;
import com.example.microsces.candidat.domaine.exception.CvNotFoundException;
import com.example.microsces.candidat.domaine.model.Cv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CvRepositoryPortAdapterTest {

    @Mock
    private CandidatJpaRepository candidatJpaRepository;

    @InjectMocks
    private CvRepositoryPortAdapter cvRepositoryPortAdapter;

    // Test de la méthode uploadCv
    @Test
    void testUploadCv_CandidatNotFound() {
        // Given
        UUID candidatId = UUID.randomUUID();
        Cv cv = new Cv(candidatId, new byte[]{1, 2, 3});

        when(candidatJpaRepository.findById(candidatId)).thenReturn(Optional.empty());

        // When & Then
        CandidatNotFoundException exception = assertThrows(CandidatNotFoundException.class, () -> {
            cvRepositoryPortAdapter.uploadCv(cv);
        });

        assertEquals("Candidat not found " + candidatId, exception.getMessage());
    }



    @Test
    void testUploadCv_Success() {
        // Given
        UUID candidatId = UUID.randomUUID();
        Cv cv = new Cv(candidatId, new byte[]{1, 2, 3});
        CandidatEntity candidatEntity = new CandidatEntity(candidatId, "John Doe", "johndoe@example.com", "0123456789", null);

        when(candidatJpaRepository.findById(candidatId)).thenReturn(Optional.of(candidatEntity));

        // When
        cvRepositoryPortAdapter.uploadCv(cv);

        // Then
        verify(candidatJpaRepository).save(candidatEntity);
    }

    // Test de la méthode downloadCv
    @Test
    void testDownloadCv_CandidatNotFound() {
        // Given
        UUID candidatId = UUID.randomUUID();

        when(candidatJpaRepository.findById(candidatId)).thenReturn(Optional.empty());

        // When & Then
        CandidatNotFoundException exception = assertThrows(CandidatNotFoundException.class, () -> {
            cvRepositoryPortAdapter.downloadCv(candidatId);
        });

        assertEquals("Candidat not found " + candidatId, exception.getMessage());
    }

    @Test
    void testDownloadCv_CvNotFound() {
        // Given
        UUID candidatId = UUID.randomUUID();
        CandidatEntity candidatEntity = new CandidatEntity(candidatId, "John Doe", "johndoe@example.com", "0123456789", null);
        Cv expectedCv = new Cv(candidatId, new byte[]{1, 2, 3});

        when(candidatJpaRepository.findById(candidatId)).thenReturn(Optional.of(candidatEntity));

        // When & Then
        CvNotFoundException exception = assertThrows(CvNotFoundException.class, () -> {
            cvRepositoryPortAdapter.downloadCv(candidatId);
        });

        assertEquals("Aucun CV trouvé pour ce candidat", exception.getMessage());
    }

    @Test
    void testDownloadCv_Success() {
        // Given
        UUID candidatId = UUID.randomUUID();
        CandidatEntity candidatEntity = new CandidatEntity(candidatId, "John Doe", "johndoe@example.com", "0123456789", new byte[]{1, 2, 3});
        Cv expectedCv = new Cv(candidatId, new byte[]{1, 2, 3});

        when(candidatJpaRepository.findById(candidatId)).thenReturn(Optional.of(candidatEntity));

        // When
        Cv actualCv = cvRepositoryPortAdapter.downloadCv(candidatId);

        // Then
        assertNotNull(actualCv);
        assertArrayEquals(expectedCv.getCvData(), actualCv.getCvData());
    }
}
