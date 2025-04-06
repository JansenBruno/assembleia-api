package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.dto.VotoRequestDTO;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @Operation(summary = "Registrar um voto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "CPF não habilitado para votar",
                    content = @Content(schema = @Schema(implementation = CpfException.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarVoto(
            @RequestBody
            @Parameter(description = "Dados do voto") VotoRequestDTO request
    ) throws CpfException {
        CpfResponseDTO cpfResponse = votoService.verificarSePodeVotar(request.getCpf());

        if (!"ABLE_TO_VOTE".equals(cpfResponse.getStatus())) {
            throw new CpfException("Associado não está habilitado para votar");
        }

        votoService.registrarVoto(request);
    }

    @Operation(summary = "Verificar se o CPF está habilitado para votar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do CPF retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "CPF inválido ou com erro de validação")
    })
    @GetMapping("/verificar/{cpf}")
    public CpfResponseDTO verificarSePodeVotar(
            @Parameter(description = "CPF do associado") @PathVariable String cpf
    ) throws CpfException {
        return votoService.verificarSePodeVotar(cpf);
    }
}
