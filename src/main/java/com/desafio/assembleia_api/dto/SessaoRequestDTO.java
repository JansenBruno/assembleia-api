package com.desafio.assembleia_api.dto;

import lombok.Data;

@Data
public class SessaoRequestDTO {
    private Long pautaId;
    private Integer duracao;
}
