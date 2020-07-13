package io.github.pameladilly.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponseDTO {

    private Long id;
    private String tipoTransacao;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private BigDecimal total;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
    private Long ativo;
    private Long usuario;
    private Long carteira;


}
