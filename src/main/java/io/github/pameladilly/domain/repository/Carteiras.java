package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Carteiras extends JpaRepository<Carteira, Long> {



}
