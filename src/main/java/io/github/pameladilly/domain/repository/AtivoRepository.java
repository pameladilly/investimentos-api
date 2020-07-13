package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {

    @Query(value = " SELECT a FROM Ativo AS a JOIN a.usuario AS u WHERE a.idAtivo = :idAtivo AND u.idUsuario = :idUsuario ")
    Optional<Ativo> findByIdAndUsuario(@Param("idAtivo") Long id, @Param( "idUsuario") Long usuario);
}
