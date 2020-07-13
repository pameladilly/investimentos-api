package io.github.pameladilly.domain.entity;

import io.github.pameladilly.domain.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransacao;

    @Column(precision = 5, scale = 3)
    private BigDecimal valorUnitario;

    @Column(precision = 4, scale = 2)
    private BigDecimal quantidade;

    @Column(precision = 10, scale = 3)
    private BigDecimal total;

    private LocalDate data;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "TipoTransacao", length = 30)
    private TipoTransacao tipoTransacao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idAtivo")
    private Ativo ativo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCarteira")
    private Carteira carteira;

}
