package io.github.pameladilly.domain.repository;

import io.github.pameladilly.domain.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    //boolean existsUsuario(Long id);

}
