package com.desafio.assembleia_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para registrar um voto em uma sessão")
public class VotoRequestDTO {

    @Schema(
            description = "ID da sessão em que o voto será registrado",
            example = "10",
            required = true
    )
    private Long idSessao;

    @Schema(
            description = "CPF do associado que está votando",
            example = "12345678901",
            required = true
    )
    private String cpf;

    @Schema(
            description = "Valor do voto: true para SIM, false para NÃO",
            example = "true",
            required = true
    )
    private Boolean voto;
}
