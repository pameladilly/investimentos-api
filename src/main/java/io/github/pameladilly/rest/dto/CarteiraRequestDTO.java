package io.github.pameladilly.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarteiraRequestDTO {

    @NotEmpty( message = "Informe a descrição")
    private String descricao;

    //@NotEmpty( message = "Informe o ID do usuário")
    @NotNull(message = "Informe o Id do usuário")
    private Long idUsuario;

}
