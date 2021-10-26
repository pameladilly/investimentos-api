package io.github.pameladilly.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarteira;

    @Column(length = 120)
    private String descricao;

    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @Column(precision = 6, scale = 2)

    private BigDecimal rentabilidade;

    @UpdateTimestamp
    private LocalDateTime ultimaAtualizacao ;

    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "carteira", fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

}
