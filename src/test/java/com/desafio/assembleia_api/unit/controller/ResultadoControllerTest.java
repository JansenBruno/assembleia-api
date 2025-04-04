package com.desafio.assembleia_api.unit.controller;

import com.desafio.assembleia_api.controller.ResultadoController;
import com.desafio.assembleia_api.dto.ResultadoResponseDTO;
import com.desafio.assembleia_api.service.ResultadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ResultadoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResultadoService resultadoService;

    @InjectMocks
    private ResultadoController resultadoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(resultadoController).build();
    }

    @Test
    void obterResultado_ComIdPautaValido_DeveRetornarResultadoResponseDTO() throws Exception {
        Long idPauta = 1L;
        ResultadoResponseDTO responseDTO = new ResultadoResponseDTO();
        responseDTO.setIdPauta(idPauta);
        responseDTO.setVotosSim(10);
        responseDTO.setVotosNao(5);
        responseDTO.setResultado("Aprovado");

        when(resultadoService.obterResultado(idPauta)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/resultados/{idPauta}", idPauta)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPauta").value(idPauta))
                .andExpect(jsonPath("$.totalVotosSim").value(10))
                .andExpect(jsonPath("$.totalVotosNao").value(5))
                .andExpect(jsonPath("$.resultado").value("Aprovado"));

        verify(resultadoService, times(1)).obterResultado(idPauta);
    }
}
