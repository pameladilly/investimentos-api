package io.github.pameladilly.rest.dto;

import javax.validation.constraints.NotNull;

public class CarteiraConsolidadaRequestDTO {

    @NotNull(message = "Informe o Id do usuário")
    private Long usuario;
}
