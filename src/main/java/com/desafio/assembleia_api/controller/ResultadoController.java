package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.ResultadoResponseDTO;
import com.desafio.assembleia_api.service.ResultadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resultados")
public class ResultadoController {

    private final ResultadoService service;

    public ResultadoController(ResultadoService service) {
        this.service = service;
    }

    @Operation(summary = "Obter o resultado da votação de uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado obtido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    })
    @GetMapping("/{idPauta}")
    public ResultadoResponseDTO obterResultado(
            @Parameter(description = "ID da pauta") @PathVariable Long idPauta) {
        return service.obterResultado(idPauta);
    }
}
