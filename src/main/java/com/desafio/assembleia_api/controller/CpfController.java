package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.service.CpfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cpf")
public class CpfController {

    private final CpfService cpfValidationService;

    public CpfController(CpfService cpfValidationService) {
        this.cpfValidationService = cpfValidationService;
    }

    @Operation(summary = "Valida um CPF usando um serviço externo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CPF válido",
                    content = @Content(schema = @Schema(implementation = CpfResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "CPF inválido ou não encontrado",
                    content = @Content(schema = @Schema(example = "{\"message\": \"CPF inválido ou inexistente\"}")))
    })
    @GetMapping("/{cpf}/validate")
    public ResponseEntity<?> validateCpf(
            @Parameter(description = "CPF a ser validado", example = "12345678901")
            @PathVariable String cpf
    ) {
        try {
            CpfResponseDTO response = cpfValidationService.validarCpf(cpf);
            return ResponseEntity.ok(response);
        } catch (CpfException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
