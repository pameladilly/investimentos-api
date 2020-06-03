package io.github.pameladilly.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "PATRIMONIO")
public class Patrimonio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPATRIMONIO")
    private Integer idPatrimonio;

    @Column(name = "RENTABILIDADE")
    private Double rentabilidade;

    @Column(name = "ULTIMAATUALIZACAO")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DATACADASTRO")
    private LocalDateTime dataCadastro;

    @JoinColumn(name = "IDUSUARIO")
    @ManyToOne
    private Usuario usuario;

}
