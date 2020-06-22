package io.github.pameladilly.rest.dto;

import io.github.pameladilly.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarteiraResponseDTO {

    private Long id;
    private String descricao;
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimaAtualizacao;
    private UsuarioResponseDTO usuarioResponseDTO;
}
