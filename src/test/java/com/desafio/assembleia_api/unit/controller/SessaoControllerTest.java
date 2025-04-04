package com.desafio.assembleia_api.unit.controller;

import com.desafio.assembleia_api.controller.SessaoController;
import com.desafio.assembleia_api.dto.SessaoRequestDTO;
import com.desafio.assembleia_api.model.Sessao;
import com.desafio.assembleia_api.service.SessaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoControllerTest {

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private SessaoController sessaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAbrirSessaoComSucesso() {
        // Arrange
        SessaoRequestDTO requestDTO = new SessaoRequestDTO();
        Sessao sessaoEsperada = new Sessao();
        when(sessaoService.abrirSessao(requestDTO)).thenReturn(sessaoEsperada);

        // Act
        Sessao resultado = sessaoController.abrirSessao(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(sessaoEsperada, resultado);
        verify(sessaoService, times(1)).abrirSessao(requestDTO);
    }

    @Test
    void deveLancarExcecaoAoAbrirSessaoComErro() {
        // Arrange
        SessaoRequestDTO requestDTO = new SessaoRequestDTO();
        when(sessaoService.abrirSessao(requestDTO)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao abrir sessão"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            sessaoController.abrirSessao(requestDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Erro ao abrir sessão", exception.getReason());
        verify(sessaoService, times(1)).abrirSessao(requestDTO);
    }
}
