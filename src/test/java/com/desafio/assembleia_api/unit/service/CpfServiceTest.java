package com.desafio.assembleia_api.unit.service;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.service.CpfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CpfServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CpfService cpfService;

    private final String CPF_VALIDO = "12345678901";
    private final String CPF_INVALIDO = "12345";

    @BeforeEach
    void setUp() {
        cpfService = new CpfService(restTemplate);
    }

    @Test
    void deveRetornarAbleToVoteParaCpfAtivo() throws CpfException {
        CpfService.ReceitawsResponse mockResponse = new CpfService.ReceitawsResponse();
        mockResponse.situacao = "ATIVA";

        when(restTemplate.getForEntity(anyString(), eq(CpfService.ReceitawsResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        CpfResponseDTO resposta = cpfService.validarCpf(CPF_VALIDO);

        assertNotNull(resposta);
        assertEquals("ABLE_TO_VOTE", resposta.getStatus());
    }

    @Test
    void deveRetornarUnableToVoteParaCpfInativo() throws CpfException {
        CpfService.ReceitawsResponse mockResponse = new CpfService.ReceitawsResponse();
        mockResponse.situacao = "INATIVA";

        when(restTemplate.getForEntity(anyString(), eq(CpfService.ReceitawsResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        CpfResponseDTO resposta = cpfService.validarCpf(CPF_VALIDO);

        assertNotNull(resposta);
        assertEquals("UNABLE_TO_VOTE", resposta.getStatus());
    }

    @Test
    void deveLancarCpfExceptionParaFormatoInvalido() {
        CpfException exception = assertThrows(CpfException.class, () -> cpfService.validarCpf(CPF_INVALIDO));
        assertEquals("Formato de CPF inválido", exception.getMessage());
    }

    @Test
    void deveLancarCpfExceptionQuandoApiFalha() throws CpfException {
        when(restTemplate.getForEntity(anyString(), eq(CpfService.ReceitawsResponse.class)))
                .thenThrow(new RuntimeException("Erro na API"));

        CpfException exception = assertThrows(CpfException.class, () -> cpfService.validarCpf(CPF_VALIDO));
        assertTrue(exception.getMessage().contains("Serviço de validação indisponível"));
    }
}
