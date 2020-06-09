package io.github.pameladilly.domain.entity;

import io.github.pameladilly.domain.enums.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
public  class Ativo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtivo;

    @Column(length = 120, nullable = false)
    private String descricao;

    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    private TipoAtivo tipoAtivo;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
    private Usuario usuario;



}
