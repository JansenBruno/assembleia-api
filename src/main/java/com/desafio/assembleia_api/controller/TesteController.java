// src/main/java/com/example/controller/TesteBancoController.java
package com.desafio.assembleia_api.controller;

import com.desafio.assembleia_api.service.TesteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste-banco")
public class TesteController {

    private final TesteService service;

    public TesteController(TesteService service) {
        this.service = service;
    }

    @GetMapping
    public String testar() {
        return service.testarConexao();
    }
}