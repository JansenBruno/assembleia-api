package com.desafio.assembleia_api.service;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.dto.VotoRequestDTO;
import com.desafio.assembleia_api.exception.BusinessException;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.exception.NotFoundException;
import com.desafio.assembleia_api.model.Sessao;
import com.desafio.assembleia_api.model.Voto;
import com.desafio.assembleia_api.repository.SessaoRepository;
import com.desafio.assembleia_api.repository.VotoRepository;
import com.desafio.assembleia_api.util.CpfValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;
    private final CpfService cpfService;

    public VotoService(VotoRepository votoRepository, SessaoRepository sessaoRepository, CpfService cpfService) {
        this.votoRepository = votoRepository;
        this.sessaoRepository = sessaoRepository;
        this.cpfService = cpfService;
    }

    // ✅ Novo método para verificar se o CPF pode votar
    public CpfResponseDTO verificarSePodeVotar(String cpf) throws CpfException {
        return cpfService.validarCpf(cpf);
    }

    public Voto registrarVoto(VotoRequestDTO request) {
        Sessao sessao = sessaoRepository.findById(request.getIdSessao())
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada"));

        if (LocalDateTime.now().isAfter(sessao.getDataHoraFim())) {
            throw new BusinessException("Sessão de votação já encerrada");
        }

        if (!CpfValidator.isValid(request.getCpf())) {
            throw new NotFoundException("CPF inválido");
        }

        if (votoRepository.existsBySessaoIdAndCpfAssociado(request.getIdSessao(), request.getCpf())) {
            throw new BusinessException("Este CPF já votou nesta sessão");
        }

        // ✅ A validação do CPF agora acontece no Controller antes de chamar registrarVoto()

        Voto voto = new Voto();
        voto.setSessao(sessao);
        voto.setCpfAssociado(request.getCpf());
        voto.setVoto(request.getVoto());

        return votoRepository.save(voto);
    }
}
