package br.com.ueder.votoonline.services;

import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.models.Setor;

import java.util.List;

public interface SetorService {

    List<DadosSetor> findAll();

    Setor findById(Long id);

    Setor save(DadosSetor entity);
}
