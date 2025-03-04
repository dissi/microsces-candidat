package com.example.microsces.candidat.domaine.service;

import com.example.microsces.candidat.domaine.exception.CandidatNotFoundException;
import com.example.microsces.candidat.domaine.model.Candidat;
import com.example.microsces.candidat.domaine.port.out.CandidatRepositoryPort;
import com.example.microsces.candidat.domaine.service.CandidatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidatServiceTest {

    @Mock
    private CandidatRepositoryPort candidatRepository;

    @InjectMocks
    private CandidatService candidatService;

    // Test de la méthode getCandidats
    @Test
    void testGetCandidats() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Candidat candidat1 = new Candidat(UUID.randomUUID(), "John Doe", "john.doe@example.com", "0123456789");
        Candidat candidat2 = new Candidat(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", "0987654321");
        Page<Candidat> page = new PageImpl<>(List.of(candidat1, candidat2));

        when(candidatRepository.findAll(pageable)).thenReturn(page);

        // When
        Page<Candidat> result = candidatService.getCandidats(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(candidat1, result.getContent().get(0));
        assertEquals(candidat2, result.getContent().get(1));
    }

    // Test de la méthode createCandidat
    @Test
    void testCreateCandidat() {
        // Given
        Candidat candidat = new Candidat(UUID.randomUUID(), "John Doe", "john.doe@example.com", "0123456789");

        when(candidatRepository.save(candidat)).thenReturn(candidat);

        // When
        Candidat result = candidatService.createCandidat(candidat);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getNom());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("0123456789", result.getTelephone());
        verify(candidatRepository).save(candidat);
    }

    // Test de la méthode getCandidatById
    @Test
    void testGetCandidatById_CandidatNotFound() {
        // Given
        UUID id = UUID.randomUUID();

        when(candidatRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        CandidatNotFoundException exception = assertThrows(CandidatNotFoundException.class, () -> {
            candidatService.getCandidatById(id);
        });

        assertEquals("Candidat not found", exception.getMessage());
    }

    @Test
    void testGetCandidatById_Success() {
        // Given
        UUID id = UUID.randomUUID();
        Candidat candidat = new Candidat(id, "John Doe", "john.doe@example.com", "0123456789");

        when(candidatRepository.findById(id)).thenReturn(Optional.of(candidat));

        // When
        Candidat result = candidatService.getCandidatById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("John Doe", result.getNom());
    }

    // Test de la méthode updateCandidat
    @Test
    void testUpdateCandidat_CandidatNotFound() {
        // Given
        UUID id = UUID.randomUUID();
        Candidat request = new Candidat(id, "John Updated", "updated.email@example.com", "0101010101");

        when(candidatRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        CandidatNotFoundException exception = assertThrows(CandidatNotFoundException.class, () -> {
            candidatService.updateCandidat(id, request);
        });

        assertEquals("Candidat not found", exception.getMessage());
    }

    @Test
    void testUpdateCandidat_Success() {
        // Given
        UUID id = UUID.randomUUID();
        Candidat existingCandidat = new Candidat(id, "John Doe", "john.doe@example.com", "0123456789");
        Candidat request = new Candidat(id, "John Updated", "updated.email@example.com", "0101010101");

        when(candidatRepository.findById(id)).thenReturn(Optional.of(existingCandidat));
        when(candidatRepository.save(existingCandidat)).thenReturn(existingCandidat);

        // When
        Candidat result = candidatService.updateCandidat(id, request);

        // Then
        assertNotNull(result);
        assertEquals("John Updated", result.getNom());
        assertEquals("updated.email@example.com", result.getEmail());
        assertEquals("0101010101", result.getTelephone());
        verify(candidatRepository).save(existingCandidat);
    }

    // Test de la méthode deleteCandidat
    @Test
    void testDeleteCandidat_CandidatNotFound() {
        // Given
        UUID id = UUID.randomUUID();

        when(candidatRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        CandidatNotFoundException exception = assertThrows(CandidatNotFoundException.class, () -> {
            candidatService.deleteCandidat(id);
        });

        assertEquals("Candidat not found", exception.getMessage());
    }

    @Test
    void testDeleteCandidat_Success() {
        // Given
        UUID id = UUID.randomUUID();
        Candidat existingCandidat = new Candidat(id, "John Doe", "john.doe@example.com", "0123456789");

        when(candidatRepository.findById(id)).thenReturn(Optional.of(existingCandidat));

        // When
        candidatService.deleteCandidat(id);

        // Then
        verify(candidatRepository).deleteById(id);
    }
}
