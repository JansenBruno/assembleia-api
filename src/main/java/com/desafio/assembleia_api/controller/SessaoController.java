package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.SessaoRequestDTO;
import com.desafio.assembleia_api.model.Sessao;
import com.desafio.assembleia_api.service.SessaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessoes")
public class SessaoController {

    private final SessaoService service;

    public SessaoController(SessaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sessao abrirSessao(@RequestBody SessaoRequestDTO request) {
        return service.abrirSessao(request);
    }
}
