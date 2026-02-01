package br.com.ueder.votoonline.services.setores;

import br.com.ueder.votoonline.converters.ConverterImpl;
import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.exceptions.ObjectNotFoundException;
import br.com.ueder.votoonline.exceptions.RNRegistroDuplicadoException;
import br.com.ueder.votoonline.models.Setor;
import br.com.ueder.votoonline.repositories.SetorRepository;
import br.com.ueder.votoonline.services.IService;
import br.com.ueder.votoonline.utils.RepositoryUtils;
import br.com.ueder.votoonline.validators.ImutableFieldValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService implements IService<Setor, DadosSetor> {

    private final SetorRepository repository;
    private ConverterImpl<Setor, DadosSetor> converter;
    private ImutableFieldValidator validator;
    private RepositoryUtils utils;

    public SetorService(SetorRepository repository, ConverterImpl<Setor, DadosSetor> converter, ImutableFieldValidator validator, RepositoryUtils utils) {
        this.repository = repository;
        this.converter = converter;
        this.validator = validator;
        this.utils = utils;
    }

    @Override
    public List<Setor> findAll() {
        return repository.findAll();
    }

    @Override
    public List<DadosSetor> getAllDTO() {
        return converter.toDto(findAll());
    }

    @Override
    public Optional<Setor> findById(Long id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.valueOf(id))));
    }

    @Override
    public Optional<DadosSetor> findByIdDTO(Long id) {
        return Optional.ofNullable(converter.toDto(findById(id).get()));
    }

    @Override
    @Transactional
    public Setor save(DadosSetor entity) {
        Setor setor = converter.toModel(entity);
        if (entity.id() == null) {
            Long _id = (Long) utils.getEntityId(Setor.class);
            setor.setId(_id);
        } else {
            Optional<Setor> setorEncontrado = repository.findById(setor.getId());
            if (setorEncontrado.isPresent()) {
                throw new RNRegistroDuplicadoException(String.valueOf(setorEncontrado.get().getId()));
            }
        }
        return repository.save(setor);
    }

    @Override
    @Transactional
    public DadosSetor saveDto(DadosSetor dto) {
        Setor setor = save(dto);
        return converter.toDto(setor);
    }

    @Override
    public Optional<Setor> findByControle(String controle) {
        return Optional.ofNullable(repository.findByControle(controle)
                .orElseThrow(() -> new ObjectNotFoundException(controle)));
    }

    @Override
    public Optional<DadosSetor> findByControleDTO(String controle) {
        return Optional.ofNullable(converter.toDto(findByControle(controle).get()));
    }

    @Override
    @Transactional
    public Setor update(String controle, DadosSetor dadosSetor) {
        Setor setorEncontrado = findByControle(controle).get();
        validator.validate("ID", setorEncontrado.getId(), dadosSetor.id());
        setorEncontrado.setDescricao(dadosSetor.descricao());
        return repository.saveAndFlush(setorEncontrado);
    }

    @Override
    @Transactional
    public DadosSetor updateDto(String controle, DadosSetor dto) {
        Setor setor = update(controle, dto);
        return converter.toDto(setor);
    }

    @Override
    @Transactional
    public void delete(String controle) {
        Setor setor = findByControle(controle).get();
        setor.excluir();
        repository.saveAndFlush(setor);
    }
}
