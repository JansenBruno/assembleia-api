package com.desafio.assembleia_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para criação de uma nova pauta")
public class PautaResquestDTO {

    @Schema(
            description = "Título da pauta",
            example = "Reforma do Estatuto",
            required = true
    )
    private String titulo;

    @Schema(
            description = "Descrição da pauta",
            example = "Discutir e votar as mudanças propostas no estatuto da associação.",
            required = true
    )
    private String descricao;
}
