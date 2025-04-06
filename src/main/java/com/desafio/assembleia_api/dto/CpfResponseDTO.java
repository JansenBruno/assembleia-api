package com.desafio.assembleia_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com o status de validação do CPF")
public class CpfResponseDTO {

    @Schema(
            description = "Status do CPF",
            example = "ABLE_TO_VOTE",
            required = true
    )
    private String status;

    public CpfResponseDTO() {
    }

    public CpfResponseDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
