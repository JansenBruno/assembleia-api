package com.desafio.assembleia_api.service;

import com.desafio.assembleia_api.dto.PautaResquestDTO;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.repository.PautaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta criarPauta(PautaResquestDTO request) {
        Pauta pauta = new Pauta();
        pauta.setTitulo(request.getTitulo());
        pauta.setDescricao(request.getDescricao());
        return pautaRepository.save(pauta);
    }

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }
}
