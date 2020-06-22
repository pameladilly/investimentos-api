package io.github.pameladilly.rest.dto;

import io.github.pameladilly.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarteiraRequestDTO {

    @NotEmpty( message = "Informe a descrição")
    private String descricao;

    private Long idUsuario;

}
