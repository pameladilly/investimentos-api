package io.github.pameladilly.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "TRANSACAO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTRANSACAO")
    private Integer idTransacao;

    @Column(name = "DATA")
    private LocalDateTime data;

    @Column(name = "TOTAL", precision = 18, scale = 2)
    private Double total;

    @Column(name = "VALORUNITARIO", precision = 18, scale = 2)
    private Double valorUnitario;

    @Column(name = "QUANTIDADE", precision = 8, scale = 2)
    private Double quantidade;

    @Column(name = "IDATIVO")
    private Integer idAtivo;

    @JoinColumn(name = "IDUSUARIO")
    @ManyToOne
    private Usuario usuario;

}
