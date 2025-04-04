package com.desafio.assembleia_api.dto;

import lombok.Data;

@Data
public class CpfResponseDTO {
    private String status; // "ABLE_TO_VOTE" ou "UNABLE_TO_VOTE"
}