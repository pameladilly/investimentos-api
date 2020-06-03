package io.github.pameladilly.domain.entity;

import io.github.pameladilly.domain.enums.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
/*@Entity*/
@Table(name = "ATIVO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDATIVO")
    private Integer id;

    @Column(name = "TICKER")
    private String ticker;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "VENCIMENTO")
    private LocalDate vencimento;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private TipoAtivo tipoAtivo;

    @JoinColumn(name = "IDUSUARIO")
    @ManyToOne
    private Usuario usuario;



}
