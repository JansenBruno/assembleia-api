package com.desafio.assembleia_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @OneToOne(mappedBy = "pauta", cascade = CascadeType.ALL)
    private Sessao sessao;
}