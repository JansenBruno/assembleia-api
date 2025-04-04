// src/main/java/com/example/repository/TesteBancoRepository.java
package com.desafio.assembleia_api.repository;

import com.desafio.assembleia_api.model.TesteBanco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TesteRepository extends JpaRepository<TesteBanco, Long> {
}