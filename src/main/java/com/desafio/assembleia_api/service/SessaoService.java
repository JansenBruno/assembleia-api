package com.desafio.assembleia_api.service;

import com.desafio.assembleia_api.dto.SessaoRequestDTO;
import com.desafio.assembleia_api.exception.NotFoundException;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.model.Sessao;
import com.desafio.assembleia_api.repository.PautaRepository;
import com.desafio.assembleia_api.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;

    public SessaoService(SessaoRepository sessaoRepository, PautaRepository pautaRepository) {
        this.sessaoRepository = sessaoRepository;
        this.pautaRepository = pautaRepository;
    }

    public Sessao abrirSessao(SessaoRequestDTO request) {
        Pauta pauta = pautaRepository.findById(request.getPautaId())
                .orElseThrow(() -> new NotFoundException("Pauta não encontrada"));

        int duracao = (request.getDuracao() != null) ? request.getDuracao() : 1;

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataHoraInicio(LocalDateTime.now());
        sessao.setDataHoraFim(LocalDateTime.now().plusMinutes(duracao)); // Definindo a duração

        return sessaoRepository.save(sessao);
    }
}
