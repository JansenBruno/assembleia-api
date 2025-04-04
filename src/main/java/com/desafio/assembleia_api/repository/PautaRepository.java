package com.desafio.assembleia_api.repository;

import com.desafio.assembleia_api.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
}