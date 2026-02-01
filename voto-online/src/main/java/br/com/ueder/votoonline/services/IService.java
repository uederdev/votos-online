package br.com.ueder.votoonline.services;

import java.util.List;
import java.util.Optional;

public interface IService <M, D> {

    List<M> findAll();

    List<D> getAllDTO();

    Optional<M> findById(Long id);

    Optional<D> findByIdDTO(Long id);

    Optional<M> findByControle(String controle);

    Optional<D> findByControleDTO(String controle);

    M save(D dto);

    D saveDto(D dto);

    void delete(String controle);

    M update(String controle, D dto);

    D updateDto(String controle, D dto);
}
