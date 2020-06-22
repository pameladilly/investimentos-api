package io.github.pameladilly.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    private Long idUsuario;

    @Column(name = "nome", length = 80, nullable = false)
    private String nome;

    @Column( length = 80 )
    private String login;

    @Column(length = 40, nullable = false)
    private String senha;

    @Column( length = 80, nullable = false)
    private String email;

    @Column( nullable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private  List<Carteira> carteiras;

}
