// src/main/java/com/example/service/TesteBancoService.java
package com.desafio.assembleia_api.service;

import com.desafio.assembleia_api.model.TesteBanco;
import com.desafio.assembleia_api.repository.TesteRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TesteService {

    private final TesteRepository repository;

    public TesteService(TesteRepository repository) {
        this.repository = repository;
    }

    public String testarConexao() {
        TesteBanco teste = new TesteBanco();
        teste.setMensagem("Teste de conexão com o banco");
        teste.setDataCriacao(LocalDateTime.now());

        repository.save(teste);

        return "Registro inserido com sucesso! ID: " + teste.getId();
    }
}