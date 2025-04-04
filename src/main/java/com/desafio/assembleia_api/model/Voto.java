package com.desafio.assembleia_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean voto; // true = Sim or false = Não

    public Voto(Sessao sessao, String cpfAssociado, Boolean voto) {
        this.sessao = sessao;
        this.cpfAssociado = cpfAssociado;
        this.voto = voto;
    }
}
