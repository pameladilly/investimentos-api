package io.github.pameladilly.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RendaFixaResponseDTO {

    private Long id;
    private String descricao;
    private LocalDateTime dataCadastro;
    private String tipoAtivo;
    private BigDecimal preco;
    private BigDecimal rentabilidadeMensal;
    private BigDecimal rentabilidadeDiaria;
    private Long usuario;

}
