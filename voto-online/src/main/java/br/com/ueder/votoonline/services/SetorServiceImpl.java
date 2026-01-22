package br.com.ueder.votoonline.services;

import br.com.ueder.votoonline.converters.SetorConverter;
import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.exceptions.ObjectNotFoundException;
import br.com.ueder.votoonline.exceptions.RNException;
import br.com.ueder.votoonline.exceptions.RNRegistroDuplicadoException;
import br.com.ueder.votoonline.models.Setor;
import br.com.ueder.votoonline.repositories.SetorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SetorServiceImpl implements SetorService{

    private final SetorRepository repository;
    private final SetorConverter converter;

    public SetorServiceImpl(SetorRepository repository, SetorConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public List<Setor> findAll() {
        return repository.findAll();
    }

    @Override
    public Setor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional
    public Setor save(DadosSetor entity) {
        Optional<Setor> setorEncontrado = repository.findById(entity.id());
        if (setorEncontrado.isPresent()){
            throw new RNRegistroDuplicadoException(String.valueOf(setorEncontrado.get().getId()));
        }
        return repository.save(converter.toModel(entity));
    }

    @Override
    public Setor findByControle(String controle) {
        return repository.findByControle(controle)
                .orElseThrow(() -> new ObjectNotFoundException(controle));
    }

    @Override
    public Setor update(String controle, DadosSetor dadosSetor) {
        Setor setorEncontrado = findByControle(controle);
        if (!dadosSetor.id().equals(setorEncontrado.getId())){
            throw new RNException("Não é possível alterar o ID " + setorEncontrado.getId());
        }
        setorEncontrado.setDescricao(dadosSetor.descricao());
        return repository.saveAndFlush(setorEncontrado);
    }
}
