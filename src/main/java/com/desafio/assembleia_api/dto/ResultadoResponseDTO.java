package com.desafio.assembleia_api.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoResponseDTO {
    private Long idPauta;
    private long votosSim;
    private long votosNao;
    private long totalVotos;
    private String resultado;
}
