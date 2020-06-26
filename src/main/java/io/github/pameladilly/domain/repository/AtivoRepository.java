package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {

}
