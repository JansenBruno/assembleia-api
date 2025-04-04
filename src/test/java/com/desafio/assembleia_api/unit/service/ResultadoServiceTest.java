package com.desafio.assembleia_api.unit.service;

import com.desafio.assembleia_api.dto.ResultadoResponseDTO;
import com.desafio.assembleia_api.exception.NotFoundException;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.model.Sessao;
import com.desafio.assembleia_api.model.Voto;
import com.desafio.assembleia_api.repository.PautaRepository;
import com.desafio.assembleia_api.service.ResultadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultadoServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private ResultadoService resultadoService;

    private Pauta pauta;
    private Sessao sessao;

    @BeforeEach
    void setUp() {
        pauta = new Pauta();
        pauta.setId(1L);

        sessao = new Sessao();
        sessao.setPauta(pauta);

        // Criando votos
        Voto votoSim1 = new Voto(sessao, "12345678901", true);
        Voto votoNao = new Voto(sessao, "98765432100", false);
        Voto votoSim2 = new Voto(sessao, "11122233344", true);

        // Convertendo List para Set
        sessao.setVotos(new HashSet<>(Arrays.asList(votoSim1, votoNao, votoSim2)));
        pauta.setSessao(sessao);
    }

    @Test
    void deveRetornarResultadoCorreto() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        ResultadoResponseDTO resultado = resultadoService.obterResultado(1L);

        assertEquals(2, resultado.getVotosSim());
        assertEquals(1, resultado.getVotosNao());
        assertEquals(3, resultado.getTotalVotos());
    }

    @Test
    void deveLancarExcecaoQuandoPautaNaoExiste() {
        when(pautaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> resultadoService.obterResultado(99L));
    }

    @Test
    void deveLancarExcecaoQuandoNaoHaSessaoNaPauta() {
        pauta.setSessao(null);
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        assertThrows(NotFoundException.class, () -> resultadoService.obterResultado(1L));
    }
}
