package com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres;

import com.example.microsces.candidat.domaine.model.Candidat;
import com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres.mapper.CandidatMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidatRepositoryPortAdapterTest {

    @Mock
    private CandidatJpaRepository candidatJpaRepository;

    @Mock
    private CandidatMapper candidatMapper;

    @InjectMocks
    private CandidatRepositoryPortAdapter candidatRepositoryPortAdapter;

    // Test de la méthode save
    @Test
    void testSave() {
        // Given
        UUID id = UUID.randomUUID();
        Candidat candidat = new Candidat(id, "John Doe", "johndoe@example.com", "0123456789");
        CandidatEntity candidatEntity = new CandidatEntity(id, "John Doe", "johndoe@example.com", "0123456789",null);

        when(candidatMapper.domainToEntity(candidat)).thenReturn(candidatEntity);
        when(candidatJpaRepository.save(candidatEntity)).thenReturn(candidatEntity);
        when(candidatMapper.entityToDomain(candidatEntity)).thenReturn(candidat);

        // When
        Candidat savedCandidat = candidatRepositoryPortAdapter.save(candidat);

        // Then
        assertNotNull(savedCandidat);
        assertEquals(candidat.getId(), savedCandidat.getId());
        verify(candidatJpaRepository).save(candidatEntity);
    }

    // Test de la méthode findById
    @Test
    void testFindById() {
        // Given
        UUID id = UUID.randomUUID();
        CandidatEntity candidatEntity = new CandidatEntity(id, "John Doe", "johndoe@example.com", "0123456789",null);
        Candidat candidat = new Candidat(id, "John Doe", "johndoe@example.com", "0123456789");

        when(candidatJpaRepository.findById(id)).thenReturn(Optional.of(candidatEntity));
        when(candidatMapper.entityToDomain(candidatEntity)).thenReturn(candidat);

        // When
        Optional<Candidat> foundCandidat = candidatRepositoryPortAdapter.findById(id);

        // Then
        assertTrue(foundCandidat.isPresent());
        assertEquals(id, foundCandidat.get().getId());
        verify(candidatJpaRepository).findById(id);
    }

    // Test de la méthode findAll
    @Test
    void testFindAll() {
        // Given
        CandidatEntity candidatEntity = new CandidatEntity(UUID.randomUUID(), "John Doe", "johndoe@example.com", "0123456789",null);
        Candidat candidat = new Candidat(UUID.randomUUID(), "John Doe", "johndoe@example.com", "0123456789");
        Page<CandidatEntity> candidatEntities = new PageImpl<>(List.of(candidatEntity));
        Page<Candidat> candidats = new PageImpl<>(List.of(candidat));

        when(candidatJpaRepository.findAll(any(Pageable.class))).thenReturn(candidatEntities);
        when(candidatMapper.entityToDomain(candidatEntity)).thenReturn(candidat);

        // When
        Page<Candidat> result = candidatRepositoryPortAdapter.findAll(Pageable.unpaged());

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(candidatJpaRepository).findAll(any(Pageable.class));
    }

    // Test de la méthode deleteById
    @Test
    void testDeleteById() {
        // Given
        UUID id = UUID.randomUUID();
        CandidatEntity candidatEntity = new CandidatEntity(id, "John Doe", "johndoe@example.com", "0123456789",null);

        when(candidatJpaRepository.findById(id)).thenReturn(Optional.of(candidatEntity));

        // When
        candidatRepositoryPortAdapter.deleteById(id);

        // Then
        verify(candidatJpaRepository).delete(candidatEntity);
    }

    // Test de la méthode deleteById lorsqu'aucun candidat n'est trouvé
    @Test
    void testDeleteById_NotFound() {
        // Given
        UUID id = UUID.randomUUID();

        when(candidatJpaRepository.findById(id)).thenReturn(Optional.empty());

        // When
        candidatRepositoryPortAdapter.deleteById(id);

        // Then
        verify(candidatJpaRepository, never()).delete(any(CandidatEntity.class));
    }
}
