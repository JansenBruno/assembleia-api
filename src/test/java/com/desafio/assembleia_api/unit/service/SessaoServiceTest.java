package com.desafio.assembleia_api.unit.service;

import com.desafio.assembleia_api.dto.SessaoRequestDTO;
import com.desafio.assembleia_api.exception.NotFoundException;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.model.Sessao;
import com.desafio.assembleia_api.repository.PautaRepository;
import com.desafio.assembleia_api.repository.SessaoRepository;
import com.desafio.assembleia_api.service.SessaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private SessaoService sessaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAbrirSessaoComSucesso() {
        SessaoRequestDTO requestDTO = new SessaoRequestDTO();
        requestDTO.setPautaId(1L);
        requestDTO.setDuracao(10);

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Sessao sessaoSalva = new Sessao();
        sessaoSalva.setPauta(pauta);
        sessaoSalva.setDataHoraInicio(LocalDateTime.now());
        sessaoSalva.setDataHoraFim(LocalDateTime.now().plusMinutes(10));

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessaoSalva);

        Sessao resultado = sessaoService.abrirSessao(requestDTO);

        assertNotNull(resultado);
        assertEquals(pauta, resultado.getPauta());
        assertEquals(10, resultado.getDataHoraFim().getMinute() - resultado.getDataHoraInicio().getMinute());
        verify(pautaRepository, times(1)).findById(1L);
        verify(sessaoRepository, times(1)).save(any(Sessao.class));
    }

    @Test
    void deveLancarExcecaoQuandoPautaNaoForEncontrada() {
        SessaoRequestDTO requestDTO = new SessaoRequestDTO();
        requestDTO.setPautaId(1L);

        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            sessaoService.abrirSessao(requestDTO);
        });

        assertEquals("Pauta não encontrada", exception.getMessage());
        verify(pautaRepository, times(1)).findById(1L);
        verify(sessaoRepository, never()).save(any(Sessao.class));
    }

    @Test
    void deveDefinirDuracaoPadraoQuandoNaoEspecificada() {
        SessaoRequestDTO requestDTO = new SessaoRequestDTO();
        requestDTO.setPautaId(1L);
        requestDTO.setDuracao(null);

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(sessaoRepository.save(any(Sessao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Sessao resultado = sessaoService.abrirSessao(requestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getDataHoraFim().getMinute() - resultado.getDataHoraInicio().getMinute()); // 1 minuto padrão
        verify(pautaRepository, times(1)).findById(1L);
        verify(sessaoRepository, times(1)).save(any(Sessao.class));
    }
}
