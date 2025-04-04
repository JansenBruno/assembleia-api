package com.desafio.assembleia_api.service;

import com.desafio.assembleia_api.dto.ResultadoResponseDTO;
import com.desafio.assembleia_api.exception.NotFoundException;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.model.Voto;
import com.desafio.assembleia_api.repository.PautaRepository;
import org.springframework.stereotype.Service;

@Service
public class ResultadoService {

    private final PautaRepository pautaRepository;

    public ResultadoService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public ResultadoResponseDTO obterResultado(Long idPauta) {
        Pauta pauta = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new NotFoundException("Pauta não encontrada"));

        if (pauta.getSessao() == null) {
            throw new NotFoundException("Nenhuma sessão encontrada para esta pauta");
        }

        long votosSim = pauta.getSessao().getVotos().stream()
                .filter(Voto::getVoto)
                .count();

        long votosNao = pauta.getSessao().getVotos().size() - votosSim;

        ResultadoResponseDTO resultado = new ResultadoResponseDTO();
        resultado.setVotosSim(votosSim);
        resultado.setVotosNao(votosNao);
        resultado.setTotalVotos(pauta.getSessao().getVotos().size());


        return resultado;
    }
}