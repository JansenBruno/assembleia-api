package com.desafio.assembleia_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para abertura de uma sessão de votação")
public class SessaoRequestDTO {

    @Schema(
            description = "ID da pauta relacionada à sessão",
            example = "1",
            required = true
    )
    private Long pautaId;

    @Schema(
            description = "Duração da sessão em minutos. Se não informado, será considerado o padrão de 1 minuto.",
            example = "5"
    )
    private Integer duracao;
}
