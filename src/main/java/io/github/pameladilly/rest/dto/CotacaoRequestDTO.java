package io.github.pameladilly.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CotacaoRequestDTO {

    @NotNull(message = "Informar o valor da cotação")
    private BigDecimal cotacao;
}
