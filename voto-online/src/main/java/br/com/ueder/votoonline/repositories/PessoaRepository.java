package br.com.ueder.votoonline.repositories;

import br.com.ueder.votoonline.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {

    @Query("select p from Pessoa p where p.excluido = false")
    List<Pessoa> findAll();

    @Query("select p from Pessoa p where p.excluido = false and p.id = :id")
    Optional<Pessoa> findById(@Param("id") Long id);

    @Query("select p from Pessoa p where p.excluido = false and p.controle = :controle")
    Optional<Pessoa> findByControle(@Param("controle") String controle);

    @Query("select p from Pessoa p where p.excluido = false and p.matricula = :matricula")
    Optional<Pessoa> findByMatricula(@Param("matricula") String matricula);

}
