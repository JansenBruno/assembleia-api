package com.desafio.assembleia_api.unit.service;

import com.desafio.assembleia_api.dto.PautaResquestDTO;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.repository.PautaRepository;
import com.desafio.assembleia_api.service.PautaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    private Pauta pauta;
    private PautaResquestDTO pautaRequestDTO;

    @BeforeEach
    void setUp() {
        pautaRequestDTO = new PautaResquestDTO();
        pautaRequestDTO.setTitulo("Pauta Teste");
        pautaRequestDTO.setDescricao("Descrição da Pauta Teste");

        pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Pauta Teste");
        pauta.setDescricao("Descrição da Pauta Teste");
    }

    @Test
    void deveCriarPautaComSucesso() {
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        Pauta resultado = pautaService.criarPauta(pautaRequestDTO);

        assertNotNull(resultado);
        assertEquals("Pauta Teste", resultado.getTitulo());
        assertEquals("Descrição da Pauta Teste", resultado.getDescricao());

        verify(pautaRepository, times(1)).save(any(Pauta.class));
    }

    @Test
    void deveListarPautasComSucesso() {
        Pauta outraPauta = new Pauta();
        outraPauta.setId(2L);
        outraPauta.setTitulo("Outra Pauta");
        outraPauta.setDescricao("Outra descrição");

        List<Pauta> pautas = Arrays.asList(pauta, outraPauta);
        when(pautaRepository.findAll()).thenReturn(pautas);

        List<Pauta> resultado = pautaService.listarPautas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Pauta Teste", resultado.get(0).getTitulo());
        assertEquals("Outra Pauta", resultado.get(1).getTitulo());

        verify(pautaRepository, times(1)).findAll();
    }
}
