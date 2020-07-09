package io.github.pameladilly.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequestDTO {

    @NotEmpty(message = "Informe o tipo da transação.")
    private String tipoTransacao;

    @NotNull(message = "Informe a data.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

    @NotNull(message = "Informe o valor unitário")
    private BigDecimal valorUnitario;

    @NotNull(message = "Informe a quantidade")
    private BigDecimal quantidade;

    @NotNull(message = "Informe o usuário")
    private Long usuario;

    @NotNull(message = "Informe o ativo")
    private Long ativo;

    @NotNull(message = "Informe a carteira")
    private Long carteira;

}
