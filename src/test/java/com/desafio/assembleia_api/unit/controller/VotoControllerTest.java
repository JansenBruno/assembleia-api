package com.desafio.assembleia_api.unit.controller;

import com.desafio.assembleia_api.controller.VotoController;
import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.dto.VotoRequestDTO;
import com.desafio.assembleia_api.service.VotoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VotoController.class)
class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Test
    void verificarSePodeVotar_CpfValido_DeveRetornar200() throws Exception {
        when(votoService.verificarSePodeVotar("12345678901")).thenReturn(new CpfResponseDTO("ABLE_TO_VOTE"));

        mockMvc.perform(get("/api/v1/votos/verificar/12345678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ABLE_TO_VOTE"));
    }

    @Test
    void registrarVoto_ComDadosValidos_DeveRetornar201() throws Exception {
        VotoRequestDTO request = new VotoRequestDTO();
        request.setCpf("12345678901");
        request.setIdSessao(1L);
        request.setVoto(true);

        mockMvc.perform(post("/api/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\":\"12345678901\",\"idSessao\":1,\"voto\":\"SIM\"}"))
                .andExpect(status().isCreated());
    }
}
