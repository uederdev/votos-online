package br.com.ueder.votoonline.services;

import br.com.ueder.votoonline.converters.SetorConverter;
import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.exceptions.ObjectNotFoundException;
import br.com.ueder.votoonline.exceptions.RNRegistroDuplicadoException;
import br.com.ueder.votoonline.models.Setor;
import br.com.ueder.votoonline.repositories.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetorServiceImpl implements SetorService{

    @Autowired
    private SetorRepository repository;
    @Autowired
    private SetorConverter converter;

    @Override
    public List<DadosSetor> findAll() {
        return List.of(new DadosSetor("123", 1l, "Ti"));
    }

    @Override
    public Setor findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(id)));
    }

    @Override
    public Setor save(DadosSetor entity) {
        Optional<Setor> setorEncontrado = repository.findById(entity.id());
        if (setorEncontrado.isPresent()){
            throw new RNRegistroDuplicadoException(String.valueOf(setorEncontrado.get().getId()));
        }
        return repository.save(converter.toModel(entity));
    }
}
