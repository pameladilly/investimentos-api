package io.github.pameladilly.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.rmi.MarshalException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    @NotEmpty(message = "Informe o nome.")
    private String nome;

    @NotEmpty(message = "Informe o login.")
    private String login;

    @NotEmpty(message = "Informe a senha")
    private String senha;

    @NotEmpty(message = "Informe a confirmação da senha")
    private String senhaConfirmacao;

    @NotEmpty(message = "Informe um e-mail")
    private String email;

}
