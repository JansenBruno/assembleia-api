package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.PautaResquestDTO;
import com.desafio.assembleia_api.model.Pauta;
import com.desafio.assembleia_api.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Criar uma nova pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pauta criarPauta(@RequestBody PautaResquestDTO request) {
        return service.criarPauta(request);
    }

    @Operation(summary = "Listar todas as pautas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pautas retornada com sucesso")
    })
    @GetMapping
    public List<Pauta> listarPautas() {
        return service.listarPautas();
    }
}
