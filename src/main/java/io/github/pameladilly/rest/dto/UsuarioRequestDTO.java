package io.github.pameladilly.rest.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRequestDTO {


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
