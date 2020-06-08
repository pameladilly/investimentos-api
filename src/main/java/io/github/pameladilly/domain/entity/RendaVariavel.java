package io.github.pameladilly.domain.entity;

import io.github.pameladilly.domain.enums.TipoAtivo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "idAtivo")
@DiscriminatorValue("RENDAVARIAVEL")
@Getter
@Setter
public class RendaVariavel extends Ativo {


    @Column(length = 5, nullable = false)
    private String ticker;
    @Column(precision = 4, scale = 2)
    private BigDecimal cotacao;


    public RendaVariavel(Long idAtivo, String descricao, LocalDateTime dataCadastro, TipoAtivo tipoAtivo, Usuario usuario, String ticker, BigDecimal cotacao) {
        super(idAtivo, descricao, dataCadastro, tipoAtivo, usuario);
        this.ticker = ticker;
        this.cotacao = cotacao;
    }
}
