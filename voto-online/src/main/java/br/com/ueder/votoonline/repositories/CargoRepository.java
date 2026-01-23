package br.com.ueder.votoonline.repositories;

import br.com.ueder.votoonline.models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, String> {

    @Query("select c from Cargo c where c.excluido = false")
    List<Cargo> findAll();

    @Query("select c from Cargo c where c.excluido = false and c.id = :id")
    Optional<Cargo> findById(@Param("id") Long id);

    @Query("select c from Cargo c where c.excluido = false and c.controle = :controle")
    Optional<Cargo> findByControle(@Param("controle") String controle);
}
