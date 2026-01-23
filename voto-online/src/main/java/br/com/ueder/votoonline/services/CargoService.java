package br.com.ueder.votoonline.services;

import br.com.ueder.votoonline.dtos.DadosCargo;
import br.com.ueder.votoonline.models.Cargo;

import java.util.List;

public interface CargoService {

    List<Cargo> findAll();

    Cargo findById(Long id);

    Cargo findByControle(String controle);

    Cargo save(DadosCargo entity);

    void delete(String controle);

    Cargo update(String controle, DadosCargo dadosCargo);
}
