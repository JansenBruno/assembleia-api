package com.desafio.assembleia_api.unit.service;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.dto.VotoRequestDTO;
import com.desafio.assembleia_api.exception.BusinessException;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.model.Sessao;
import com.desafio.assembleia_api.model.Voto;
import com.desafio.assembleia_api.repository.SessaoRepository;
import com.desafio.assembleia_api.repository.VotoRepository;
import com.desafio.assembleia_api.service.CpfService;
import com.desafio.assembleia_api.service.VotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private CpfService cpfService;

    @InjectMocks
    private VotoService votoService;

    private VotoRequestDTO votoRequest;
    private Sessao sessao;

    @BeforeEach
    void setUp() {
        votoRequest = new VotoRequestDTO();
        votoRequest.setCpf("12345678901");
        votoRequest.setIdSessao(1L);
        votoRequest.setVoto(true);

        sessao = new Sessao();
        sessao.setId(1L);
        sessao.setDataHoraFim(LocalDateTime.now().plusMinutes(10));
    }

    @Test
    void registrarVoto_ComDadosValidos_DeveSalvarVoto() throws CpfException {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoIdAndCpfAssociado(1L, "12345678901")).thenReturn(false);
        when(cpfService.validarCpf("12345678901")).thenReturn(new CpfResponseDTO("ABLE_TO_VOTE"));

        Voto voto = votoService.registrarVoto(votoRequest);

        assertNotNull(voto);
        assertEquals("12345678901", voto.getCpfAssociado());
        assertEquals("SIM", voto.getVoto());
        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    void registrarVoto_ComSessaoEncerrada_DeveLancarBusinessException() {
        sessao.setDataHoraFim(LocalDateTime.now().minusMinutes(1));
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            votoService.registrarVoto(votoRequest);
        });

        assertEquals("Sessão de votação já encerrada", exception.getMessage());
    }

    @Test
    void registrarVoto_ComCpfJaRegistrado_DeveLancarBusinessException() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoIdAndCpfAssociado(1L, "12345678901")).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            votoService.registrarVoto(votoRequest);
        });

        assertEquals("Este CPF já votou nesta sessão", exception.getMessage());
    }

    @Test
    void registrarVoto_ComCpfInvalido_DeveLancarCpfException() throws CpfException {
        when(cpfService.validarCpf("12345678901")).thenThrow(new CpfException("CPF inválido"));

        CpfException exception = assertThrows(CpfException.class, () -> {
            votoService.registrarVoto(votoRequest);
        });

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void registrarVoto_ComSessaoInexistente_DeveLancarBusinessException() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            votoService.registrarVoto(votoRequest);
        });

        assertEquals("Sessão não encontrada", exception.getMessage());
    }

    @Test
    void registrarVoto_ErroAoSalvarVoto_DeveLancarBusinessException() throws CpfException {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoIdAndCpfAssociado(1L, "12345678901")).thenReturn(false);
        when(cpfService.validarCpf("12345678901")).thenReturn(new CpfResponseDTO("ABLE_TO_VOTE"));
        doThrow(new RuntimeException("Erro ao salvar voto")).when(votoRepository).save(any(Voto.class));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            votoService.registrarVoto(votoRequest);
        });

        assertEquals("Erro ao registrar voto", exception.getMessage());
    }

    @Test
    void verificarSePodeVotar_CpfValido_DeveRetornarCpfResponseDTO() throws CpfException {
        when(cpfService.validarCpf("12345678901")).thenReturn(new CpfResponseDTO("ABLE_TO_VOTE"));

        CpfResponseDTO response = votoService.verificarSePodeVotar("12345678901");

        assertNotNull(response);
        assertEquals("ABLE_TO_VOTE", response.getStatus());
    }

    @Test
    void verificarSePodeVotar_CpfInvalido_DeveLancarCpfException() throws CpfException {
        when(cpfService.validarCpf("12345678901")).thenThrow(new CpfException("CPF inválido"));

        CpfException exception = assertThrows(CpfException.class, () -> {
            votoService.verificarSePodeVotar("12345678901");
        });

        assertEquals("CPF inválido", exception.getMessage());
    }
}
