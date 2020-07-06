package io.github.pameladilly.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RendaVariavelRequestDTO {

    @NotEmpty(message = "Informe a descrição.")
    private String descricao;

    @NotEmpty(message = "Informe o Ticker.")
    private String ticker;

    @NotNull(message = "Informe a cotação.")
    private BigDecimal cotacao;

    @NotNull(message = "Informe o Id do usuário")
    private Long usuario;
}
