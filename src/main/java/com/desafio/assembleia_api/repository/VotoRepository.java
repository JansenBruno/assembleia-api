package com.desafio.assembleia_api.repository;

import com.desafio.assembleia_api.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsBySessaoIdAndCpfAssociado(Long sessaoId, String cpfAssociado);
}