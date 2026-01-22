package br.com.ueder.votoonline.services;

import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.models.Setor;

import java.util.List;

public interface SetorService {

    List<Setor> findAll();

    Setor findById(Long id);

    Setor save(DadosSetor entity);

    Setor findByControle(String controle);

    Setor update(String controle, DadosSetor dadosSetor);

}
