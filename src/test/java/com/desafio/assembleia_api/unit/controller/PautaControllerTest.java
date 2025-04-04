package com.desafio.assembleia_api.unit.controller;

import com.desafio.assembleia_api.controller.PautaController;
import com.desafio.assembleia_api.dto.PautaResquestDTO;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.service.PautaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaService pautaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pauta pauta;
    private PautaResquestDTO pautaRequest;

    @BeforeEach
    void setUp() {
        pautaRequest = new PautaResquestDTO();
        pautaRequest.setTitulo("Nova Pauta");
        pautaRequest.setDescricao("Descrição da nova pauta");

        pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Nova Pauta");
        pauta.setDescricao("Descrição da nova pauta");
    }

    @Test
    void deveCriarPautaComSucesso() throws Exception {
        when(pautaService.criarPauta(any(PautaResquestDTO.class))).thenReturn(pauta);

        mockMvc.perform(post("/api/v1/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Nova Pauta"))
                .andExpect(jsonPath("$.descricao").value("Descrição da nova pauta"));

        verify(pautaService, times(1)).criarPauta(any(PautaResquestDTO.class));
    }

    @Test
    void deveListarTodasPautas() throws Exception {
        when(pautaService.listarPautas()).thenReturn(List.of(pauta));

        mockMvc.perform(get("/api/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Nova Pauta"))
                .andExpect(jsonPath("$[0].descricao").value("Descrição da nova pauta"));

        verify(pautaService, times(1)).listarPautas();
    }
}
