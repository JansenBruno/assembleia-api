package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.ResultadoResponseDTO;
import com.desafio.assembleia_api.service.ResultadoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resultados")
public class ResultadoController {

    private final ResultadoService service;

    public ResultadoController(ResultadoService service) {
        this.service = service;
    }

    @GetMapping("/{idPauta}")
    public ResultadoResponseDTO obterResultado(@PathVariable Long idPauta) {
        return service.obterResultado(idPauta);
    }
}