package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.dto.VotoRequestDTO;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.service.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarVoto(@RequestBody VotoRequestDTO request) throws CpfException {
        votoService.registrarVoto(request);
    }


    @GetMapping("/verificar/{cpf}")
    public CpfResponseDTO verificarSePodeVotar(@PathVariable String cpf) throws CpfException {
        return votoService.verificarSePodeVotar(cpf);
    }
}
