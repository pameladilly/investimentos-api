package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.RendaVariavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendaVariavelRepository extends JpaRepository<RendaVariavel, Long> {

}
