package io.github.pameladilly.domain.entity;


import io.github.pameladilly.domain.enums.TipoAtivo;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "idAtivo")
@DiscriminatorValue("RENDAFIXA")
@Getter
@Setter
public class RendaFixa extends Ativo {


    @Column(precision = 6, scale = 3)
    private BigDecimal rentabilidadeDiaria;


    @Column(nullable = false)
    private LocalDate vencimento;

    @Column(precision = 8, scale = 3, nullable = false)
    private BigDecimal preco;


    public RendaFixa(Long idAtivo, String descricao, LocalDateTime dataCadastro, TipoAtivo tipoAtivo, Usuario usuario, BigDecimal rentabilidadeDiaria, LocalDate vencimento, BigDecimal preco) {
        super(idAtivo, descricao, dataCadastro, tipoAtivo, usuario);
        this.rentabilidadeDiaria = rentabilidadeDiaria;
        this.vencimento = vencimento;
        this.preco = preco;


    }
}
