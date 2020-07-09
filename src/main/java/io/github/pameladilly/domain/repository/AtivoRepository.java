package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {

    @Query(value = " SELECT * FROM ATIVO WHERE ID_ATIVO = :ID AND USUARIO_ID_USUARIO = :USUARIO ", nativeQuery = true)
    Optional<Ativo> findByIdAndUsuario(@Param("ID") Long id, @Param( "USUARIO") Long usuario);
}
