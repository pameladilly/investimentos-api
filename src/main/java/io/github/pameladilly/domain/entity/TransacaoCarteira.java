package io.github.pameladilly.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "TRANSACAO_CARTEIRA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoCarteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTRANSACAO_CARTEIRA")
    private Integer idTransacaoCarteira;

    @Column(name = "DATAVINCULO")
    private LocalDateTime dataVinculo;

    @JoinColumn(name = "IDCARTEIRA")
    @OneToMany(mappedBy = "CARTEIRA")
    private Set<Carteira> carteiras;

    @JoinColumn(name = "IDTRANSACAO")
    @OneToMany(mappedBy = "TRANSACAO")
    private Set<Transacao> transacaos;

    @JoinColumn(name = "IDUSUARIO")
    @ManyToOne
    private Usuario usuario;

}
