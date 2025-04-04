package com.desafio.assembleia_api.dto;

import lombok.Data;

@Data
public class ResultadoResponseDTO {
    private Long votosSim;
    private Long votosNao;
    private Long totalVotos;
}