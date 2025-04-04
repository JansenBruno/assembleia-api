package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.dto.CpfResponseDTO;
import com.desafio.assembleia_api.exception.CpfException;
import com.desafio.assembleia_api.service.CpfService;
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

    @GetMapping("/{cpf}/validate")
    public ResponseEntity<?> validateCpf(@PathVariable String cpf) {
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