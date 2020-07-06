package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.entity.RendaFixa;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendaFixaRepository extends JpaRepository<RendaFixa, Long> {

}
