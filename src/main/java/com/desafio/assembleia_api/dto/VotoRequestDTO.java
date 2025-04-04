package com.desafio.assembleia_api.dto;

import lombok.Data;

@Data
public class VotoRequestDTO {
    private Long idSessao;
    private String cpf;
    private Boolean voto;
}