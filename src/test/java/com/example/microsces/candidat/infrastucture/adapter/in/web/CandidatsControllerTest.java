package com.example.microsces.candidat.infrastucture.adapter.in.web;

import com.example.microsces.candidat.domaine.model.Candidat;
import com.example.microsces.candidat.domaine.model.Cv;
import com.example.microsces.candidat.domaine.port.in.CandidatUseCase;
import com.example.microsces.candidat.domaine.port.in.CvFileUseCase;
import com.example.microsces.candidat.infrastucture.adapter.in.web.mapper.CandidatInMapper;
import com.example.microsces.candidat.controller.api.model.CandidatRequest;
import com.example.microsces.candidat.controller.api.model.CandidatResponse;
import com.example.microsces.candidat.controller.api.model.PageCandidatResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CandidatsControllerTest {

    @Mock
    private CandidatUseCase candidatService;

    @Mock
    private CvFileUseCase cvFileService;

    @Mock
    private CandidatInMapper candidatInMapper;

    @InjectMocks
    private CandidatsController candidatsController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(candidatsController).build();
    }

    // Test de la méthode createCandidat
    @Test
    void testCreateCandidat() throws Exception {
        UUID candidatId = UUID.randomUUID();
        Candidat candidat =new Candidat(candidatId, "John Doe", "john.doe@example.com", "0123456789");
        CandidatResponse candidatResponse = new CandidatResponse()
                .id(candidatId)
                .nom("John Doe")
                .email("john.doe@example.com")
                .telephone("0123456789");


        when(candidatInMapper.requestToDomain(any(CandidatRequest.class))).thenReturn(candidat);
        when(candidatService.createCandidat(any(Candidat.class))).thenReturn(candidat);
        when(candidatInMapper.domainToResponse(any(Candidat.class))).thenReturn(candidatResponse);
        System.out.println("candidatResponse = " + candidatResponse);
        mockMvc.perform(post("/candidats")
                        .contentType("application/json")
                        .content("{ \"nom\": \"John Doe\", \"email\": \"john.doe@example.com\", \"telephone\": \"0123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    // Test de la méthode deleteCandidat
    @Test
    void testDeleteCandidat() throws Exception {
        UUID candidatId = UUID.randomUUID();
        doNothing().when(candidatService).deleteCandidat(candidatId);

        mockMvc.perform(delete("/candidats/{candidatId}", candidatId))
                .andExpect(status().isOk());
    }

    // Test de la méthode getCandidatById
    @Test
    void testGetCandidatById() throws Exception {
        UUID candidatId = UUID.randomUUID();
        CandidatResponse candidatResponse = new CandidatResponse()
                .id(candidatId)
                .nom("John Doe")
                .email("john.doe@example.com")
                .telephone("0123456789");
        when(candidatService.getCandidatById(candidatId)).thenReturn(new Candidat(candidatId, "John Doe", "john.doe@example.com", "0123456789"));
        when(candidatInMapper.domainToResponse(any(Candidat.class))).thenReturn(candidatResponse);

        mockMvc.perform(get("/candidats/{candidatId}", candidatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    // Test de la méthode getCandidats
    @Test
    void testGetCandidats() throws Exception {
        // Création d'une liste de candidats simulée
        UUID candidatId1 = UUID.randomUUID();
        UUID candidatId2 = UUID.randomUUID();

        List<Candidat> candidatsList = List.of(
                new Candidat(candidatId1, "John Doe", "john.doe@example.com", "0123456789"),
                new Candidat(candidatId2, "Jane Doe", "jane.doe@example.com", "0987654321")
        );

        // Création d'un objet Page de candidats
        Pageable pageable = PageRequest.of(0, 10); // Paramètres de pagination
        Page<Candidat> pageCandidats = new PageImpl<>(candidatsList, pageable, candidatsList.size());

        List<CandidatResponse> candidatResponseList = List.of(
                new CandidatResponse()
                .id(candidatId1)
                .nom("John Doe")
                .email("john.doe@example.com")
                .telephone("0123456789"),
                new CandidatResponse()
                .id(candidatId2)
                .nom("Jane Doe")
                .email("jane.doe@example.com")
                .telephone("0987654321")
        );
        PageCandidatResponse pageCandidatResponse = new PageCandidatResponse();
        pageCandidatResponse.setContent(candidatResponseList);
        pageCandidatResponse.setTotalPages(1);
        pageCandidatResponse.setTotalElements(2);
        pageCandidatResponse.setPageSize(10);

        // Simulation du service pour renvoyer le Page<Candidat>
        when(candidatService.getCandidats(Mockito.any(Pageable.class))).thenReturn(pageCandidats);
        when(candidatInMapper.toPageCandidatResponse(any(Page.class))).thenReturn(pageCandidatResponse);
        // Exécution de la requête GET et vérification de la réponse
        mockMvc.perform(get("/candidats")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())  // Vérifie que la réponse est 200 OK
                .andExpect(jsonPath("$.content[0].nom").value("John Doe"))  // Vérifie le nom du premier candidat
                .andExpect(jsonPath("$.content[1].nom").value("Jane Doe"))  // Vérifie le nom du deuxième candidat
                .andExpect(jsonPath("$.content[0].email").value("john.doe@example.com"))  // Vérifie l'email du premier candidat
                .andExpect(jsonPath("$.content[1].email").value("jane.doe@example.com"))  // Vérifie l'email du deuxième candidat
                .andExpect(jsonPath("$.content[0].telephone").value("0123456789"))  // Vérifie le téléphone du premier candidat
                .andExpect(jsonPath("$.content[1].telephone").value("0987654321"))  // Vérifie le téléphone du deuxième candidat
                .andExpect(jsonPath("$.totalElements").value(2))  // Vérifie le nombre total d'éléments (2 dans ce cas)
                .andExpect(jsonPath("$.totalPages").value(1))  // Vérifie le nombre total de pages (1 page avec 2 candidats)
                .andExpect(jsonPath("$.pageSize").value(10));  // Vérifie la taille de la page (10 éléments par page)
    }

    // Test de la méthode updateCandidat
    @Test
    void testUpdateCandidat() throws Exception {
        UUID candidatId = UUID.randomUUID();
        Candidat candidat = new Candidat(candidatId, "John Doe", "john.doe@example.com", "0123456789");
        CandidatResponse candidatResponse = new CandidatResponse()
                .id(candidatId)
                .nom("John Doe")
                .email("john.doe@example.com")
                .telephone("0123456789");
        when(candidatInMapper.requestToDomain(any(CandidatRequest.class))).thenReturn(candidat);

        when(candidatService.updateCandidat(eq(candidatId), any(Candidat.class))).thenReturn(candidat);
        when(candidatInMapper.domainToResponse(any(Candidat.class))).thenReturn(candidatResponse);

        mockMvc.perform(put("/candidats/{candidatId}", candidatId)
                        .contentType("application/json")
                        .content("{ \"nom\": \"John Doe\", \"email\": \"john.doe@example.com\", \"telephone\": \"0123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.telephone").value("0123456789"));
    }

    // Test de la méthode uploadCv
    @Test
    void testUploadCv() throws Exception {
        UUID candidatId = UUID.randomUUID();
        MockMultipartFile file = new MockMultipartFile("file", "cv.pdf", "application/pdf", "some content".getBytes());

        doNothing().when(cvFileService).uploadCv(any(Cv.class));

        mockMvc.perform(multipart("/candidats/{candidatId}/cv", candidatId)
                        .file(file))
                .andExpect(status().isOk());
    }

    // Test de la méthode downloadCv
    @Test
    void testDownloadCv() throws Exception {
        UUID candidatId = UUID.randomUUID();
        byte[] cvData = "some content".getBytes();
        Cv cv = new Cv(candidatId, cvData);

        when(cvFileService.downloadCv(candidatId)).thenReturn(cv);

        mockMvc.perform(get("/candidats/{candidatId}/cv", candidatId))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CV_" + candidatId + ".pdf"))
                .andExpect(content().bytes(cvData));
    }
}
