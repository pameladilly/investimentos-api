package io.github.pameladilly.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "CARTEIRA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCARTEIRA")
    private Integer idCarteira;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "RENTABILIDADE")
    private Double rentabilidade;

    @Column(name = "ULTIMAATUALIZACAO")
    private LocalDateTime ultimaAtualizacao;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO")
    private Usuario usuario;


}
