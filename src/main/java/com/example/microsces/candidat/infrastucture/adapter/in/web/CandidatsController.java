package com.example.microsces.candidat.infrastucture.adapter.in.web;


import com.example.microsces.candidat.domaine.model.Cv;
import com.example.microsces.candidat.domaine.port.in.CandidatUseCase;
import com.example.microsces.candidat.domaine.port.in.CvFileUseCase;
import com.example.microsces.candidat.infrastucture.adapter.in.web.mapper.CandidatInMapper;
import com.example.microsces.candidat.controller.api.CandidatsApi;
import com.example.microsces.candidat.controller.api.model.CandidatRequest;
import com.example.microsces.candidat.controller.api.model.CandidatResponse;
import com.example.microsces.candidat.controller.api.model.PageCandidatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.example.microsces.candidat.domaine.model.Cv.createFromMultipartFile;


/**
 * End-point d'accès aux ressources de Candidat
 *
 * @author Dissirama ESSO
 * @version 1.0
 * @date 01/03/2025
 *
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class CandidatsController implements CandidatsApi {

    private final CandidatUseCase candidatService;
    private final CvFileUseCase cvFileService;
    private final CandidatInMapper candidatInMapper;
    @Override
    public ResponseEntity<CandidatResponse> createCandidat(CandidatRequest candidatRequest) throws Exception {
        log.info("Adding a new candidat");
        return ResponseEntity.ok(candidatInMapper.domainToResponse(candidatService.createCandidat(candidatInMapper.requestToDomain(candidatRequest))));
    }

    @Override
    public ResponseEntity<Void> deleteCandidat(UUID candidatId) throws Exception {
        log.info("Delete a candidat");
        candidatService.deleteCandidat(candidatId);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<CandidatResponse> getCandidatById(UUID candidatId) throws Exception {
        return ResponseEntity.ok(candidatInMapper.domainToResponse(candidatService.getCandidatById(candidatId)));

    }

    @Override
    public ResponseEntity<PageCandidatResponse> getCandidats(Integer page, Integer size) throws Exception {
        return ResponseEntity.ok(candidatInMapper.toPageCandidatResponse(candidatService.getCandidats(PageRequest.of(page,size))));
    }

    @Override
    public ResponseEntity<CandidatResponse> updateCandidat(UUID candidatId, CandidatRequest candidatRequest) throws Exception {
        return ResponseEntity.ok(candidatInMapper.domainToResponse(candidatService.updateCandidat(candidatId, candidatInMapper.requestToDomain(candidatRequest))));

    }

    /**
     * POST /candidats/{candidatId}/cv : Upload du CV d&#39;un candidat
     *
     * @param candidatId (required)
     * @param file       (optional)
     * @return Fichier CV uploadé avec succès (status code 200)
     * or Requête invalide (status code 400)
     * or Candidat non trouvé (status code 404)
     */
    @Override
    public ResponseEntity<Void> uploadCv(UUID candidatId, MultipartFile file) throws Exception {
        Cv cv = createFromMultipartFile(candidatId, file);
        cvFileService.uploadCv(cv);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Resource> downloadCv(UUID candidatId) throws Exception {
        Cv cv=  cvFileService.downloadCv(candidatId);
        // Créer une ressource à partir des données du CV
        ByteArrayResource resource = new ByteArrayResource(cv.getCvData());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CV_" + candidatId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}
