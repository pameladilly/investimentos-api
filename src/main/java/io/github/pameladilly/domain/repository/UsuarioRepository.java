package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

   // Optional<Usuario> findByEmail(String email);


    Optional<Usuario> findByLogin(String login);

    boolean existsById(Long id);
}
