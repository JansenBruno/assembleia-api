package com.desafio.assembleia_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Resultado da votação de uma pauta")
public class ResultadoResponseDTO {

    @Schema(description = "ID da pauta", example = "1")
    private Long idPauta;

    @Schema(description = "Quantidade de votos SIM", example = "35")
    private long votosSim;

    @Schema(description = "Quantidade de votos NÃO", example = "12")
    private long votosNao;

    @Schema(description = "Total de votos", example = "47")
    private long totalVotos;

    @Schema(description = "Resultado final da votação", example = "APROVADA")
    private String resultado;
}
