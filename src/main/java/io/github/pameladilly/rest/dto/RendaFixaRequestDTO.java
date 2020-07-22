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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RendaFixaRequestDTO {

    @NotEmpty(message = "Informe a descrição")
    private String descricao;

  //  @NotEmpty(message = "Informe o tipo do Ativo")
   // private String tipoAtivo;

    @NotNull(message = "Informe a rentabilidade diária")
    private BigDecimal rentabilidadeDiaria;

    @NotNull(message = "Informe o vencimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate vencimento;

    @NotNull(message = "Informe o preço")
    private BigDecimal preco;

    @NotNull(message = "Informe a rentabilidade mensal")
    private BigDecimal rentabilidadeMensal;

    @NotNull(message = "Informe o ID do usuário")
    private Long usuario;
}
