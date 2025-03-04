package com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres.mapper;

import com.example.microsces.candidat.domaine.model.Candidat;
import com.example.microsces.candidat.infrastucture.adapter.out.persitence.postgres.CandidatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CandidatMapper {

    CandidatEntity domainToEntity(Candidat candidat);
    Candidat entityToDomain(CandidatEntity entity);
}
