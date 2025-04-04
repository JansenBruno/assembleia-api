package com.desafio.assembleia_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    private Sessao sessao;

    @Column(nullable = false)
    private String cpfAssociado;

    @Column(nullable = false)
    private Boolean voto; // true = Sim, false = Não
}