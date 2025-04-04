package com.desafio.assembleia_api.repository;

import com.desafio.assembleia_api.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
}