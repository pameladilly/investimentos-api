package io.github.pameladilly.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String login;
    private String email;
    private LocalDateTime dataCadastro;
}
