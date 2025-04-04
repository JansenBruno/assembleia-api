package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.PautaResquestDTO;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.service.PautaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    private final PautaService service;

    public PautaController(PautaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pauta criarPauta(@RequestBody PautaResquestDTO request) {
        return service.criarPauta(request);
    }

    @GetMapping
    public List<Pauta> listarPautas() {
        return service.listarPautas();
    }
}

