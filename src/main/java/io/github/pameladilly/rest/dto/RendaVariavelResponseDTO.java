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
public class RendaVariavelResponseDTO {

    private Long id;
    private String descricao;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private String ticker;
    private BigDecimal cotacao;
    private Long usuario;

}
