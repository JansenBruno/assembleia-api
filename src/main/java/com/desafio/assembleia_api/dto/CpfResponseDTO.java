package com.desafio.assembleia_api.dto;

public class CpfResponseDTO {
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
