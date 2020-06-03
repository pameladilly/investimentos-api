package io.github.pameladilly.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO")
    private Integer idUsuario;

    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;

    @Column(name = "LOGIN", length = 80 , nullable = true)
    private String login;

    @Column(name = "SENHA", length = 40, nullable = false)
    private String senha;

    @Column(name = "EMAIL", length = 80, nullable = false)
    private String email;

    @Column(name = "DATACADASTRO", nullable = false)
    private LocalDateTime dataCadastro;

}
