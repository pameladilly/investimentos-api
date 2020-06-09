package io.github.pameladilly;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.Usuarios;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class InvestimentosApplication {


 /*   @Bean
    public CommandLineRunner init(Usuarios repository) {
        return  args -> {
            Usuario user = Usuario.builder()
                            .dataCadastro(LocalDateTime.now())
                            .email("pamela@hotmail.com")
                            .nome("Pamela")
                            .senha("123")
                            .login("pameladilly")
                            .build();
            repository.save(user);

            System.out.println("Ok - entrou aqui!");

            List<Usuario> query = repository.findAll();

            query.forEach( (u) -> {
                System.out.println("#1 " + u);
            });

            for (Usuario usuario : query) {
                System.out.println("Meu usuário: " + usuario);
            }

        };


    }*/
    public static void main(String[] args) {

        SpringApplication.run(InvestimentosApplication.class, args);

    }
}
