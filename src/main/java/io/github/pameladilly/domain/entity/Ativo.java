package io.github.pameladilly.domain.entity;

import io.github.pameladilly.domain.enums.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Ativo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtivo;

    @Column(length = 120, nullable = false)
    private String descricao;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(updatable = true)
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    private TipoAtivo tipoAtivo;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
    private Usuario usuario;


}
