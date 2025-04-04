package com.desafio.assembleia_api.unit.controller;

import com.desafio.assembleia_api.controller.CpfController;
import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.service.CpfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CpfController.class)
class CpfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CpfService cpfService;

    private final String VALID_CPF = "12345678901";
    private final String INVALID_CPF = "11111111111";

    @BeforeEach
    void setUp() {
    }

    @Test
    void validateCpf_ComCpfValido_DeveRetornarStatus200() throws Exception {
        when(cpfService.validarCpf(VALID_CPF)).thenReturn(new CpfResponseDTO("ABLE_TO_VOTE"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cpf/{cpf}/validate", VALID_CPF)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ABLE_TO_VOTE"));
    }

    @Test
    void validateCpf_ComCpfInvalido_DeveRetornarStatus404() throws Exception {
        when(cpfService.validarCpf(INVALID_CPF)).thenThrow(new CpfException("CPF inválido"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cpf/{cpf}/validate", INVALID_CPF)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("CPF inválido"));
    }
}
