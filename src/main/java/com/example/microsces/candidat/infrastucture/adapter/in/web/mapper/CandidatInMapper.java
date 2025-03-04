package com.example.microsces.candidat.infrastucture.adapter.in.web.mapper;

import com.example.microsces.candidat.domaine.model.Candidat;
import com.example.microsces.candidat.controller.api.model.CandidatRequest;
import com.example.microsces.candidat.controller.api.model.CandidatResponse;
import com.example.microsces.candidat.controller.api.model.PageCandidatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CandidatInMapper {


   CandidatResponse domainToResponse(Candidat candidat);

    @Mapping(target = "id", ignore = true)
    Candidat requestToDomain(CandidatRequest request);

    @Mapping(target = "content", source = "page.content")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "pageSize", source = "page.size")
    @Mapping(target = "currentPage", source = "page.number")
    PageCandidatResponse toPageCandidatResponse(Page<Candidat> page);
    List<CandidatResponse> toCandidatResponseList(List<Candidat> candidats);
}
