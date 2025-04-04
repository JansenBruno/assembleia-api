package com.desafio.assembleia_api.service;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.exception.CpfException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CpfService {
    private final RestTemplate restTemplate;

    @Value("${receitaws.api.url}")
    private String receitawsApiUrl;

    public CpfService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CpfResponseDTO validarCpf(String cpf) throws CpfException {
        if (!validarFormatoCpf(cpf)) {
            throw new CpfException("Formato de CPF inválido");
        }

        try {
            // 2. Consulta à API da ReceitaWS
            String url = receitawsApiUrl + cpf;
            ResponseEntity<ReceitawsResponse> response = restTemplate.getForEntity(
                    url,
                    ReceitawsResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return mapearResposta(response.getBody());
            }
            throw new CpfException("Erro ao consultar CPF");

        } catch (Exception e) {
            throw new CpfException("Serviço de validação indisponível: " + e.getMessage(), e);
        }
    }

    private boolean validarFormatoCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    private CpfResponseDTO mapearResposta(ReceitawsResponse receitawsResponse) {
        CpfResponseDTO response = new CpfResponseDTO();
        response.setStatus("ATIVA".equals(receitawsResponse.getSituacao()) ?
                "ABLE_TO_VOTE" : "UNABLE_TO_VOTE");
        return response;
    }

    private static class ReceitawsResponse {
        private String situacao;

        public String getSituacao() {
            return situacao;
        }
    }
}
