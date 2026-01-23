package br.com.ueder.votoonline.repositories;

import br.com.ueder.votoonline.models.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, String> {

    @Query("select s from Setor s where s.excluido = false and s.id = :id ")
    Optional<Setor> findById(@Param("id") Long id);

    @Query("select s from Setor s where s.excluido = false and s.controle = :controle ")
    Optional<Setor> findByControle(@Param("controle")String controle);

    @Query("select s from Setor s where s.excluido = false")
    List<Setor> findAll();
}
