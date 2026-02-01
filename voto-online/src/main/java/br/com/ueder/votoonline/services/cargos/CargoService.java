package br.com.ueder.votoonline.services.cargos;

import br.com.ueder.votoonline.converters.entities.CargoConverter;
import br.com.ueder.votoonline.dtos.DadosCargo;
import br.com.ueder.votoonline.exceptions.ObjectNotFoundException;
import br.com.ueder.votoonline.exceptions.RNRegistroDuplicadoException;
import br.com.ueder.votoonline.models.Cargo;
import br.com.ueder.votoonline.repositories.CargoRepository;
import br.com.ueder.votoonline.services.IService;
import br.com.ueder.votoonline.utils.RepositoryUtils;
import br.com.ueder.votoonline.validators.ImutableFieldValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService implements IService<Cargo, DadosCargo> {

    private final CargoRepository repository;
    private final CargoConverter converter;
    private final RepositoryUtils repositoryUtils;
    private final ImutableFieldValidator validador;

    public CargoService(CargoRepository repository, CargoConverter converter, RepositoryUtils repositoryUtils, ImutableFieldValidator validador) {
        this.repository = repository;
        this.converter = converter;
        this.repositoryUtils = repositoryUtils;
        this.validador = validador;
    }

    @Override
    public List<Cargo> findAll() {
        return repository.findAll();
    }

    @Override
    public List<DadosCargo> getAllDTO() {
        return converter.toDto(repository.findAll());
    }

    @Override
    public Optional<Cargo> findById(Long id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.valueOf(id))));
    }

    @Override
    public Optional<DadosCargo> findByIdDTO(Long id) {
        return Optional.ofNullable(converter.toDto(findById(id).get()));
    }

    @Override
    public Optional<Cargo> findByControle(String controle) {
        return Optional.ofNullable(repository.findByControle(controle)
                .orElseThrow(() -> new ObjectNotFoundException(controle)));
    }

    @Override
    public Optional<DadosCargo> findByControleDTO(String controle) {
        return Optional.ofNullable(converter.toDto(findByControle(controle).get()));
    }

    @Override
    @Transactional
    public Cargo save(DadosCargo entity) {
        Cargo model = converter.toModel(entity);
        if (model.getId() == null){
            Long entityId = (Long) repositoryUtils.getEntityId(Cargo.class);
            model.setId(entityId);
        } else {
            Optional<Cargo> cargoEncontrado = repository.findById(entity.id());
            if (cargoEncontrado.isPresent()){
                throw new RNRegistroDuplicadoException(String.valueOf(cargoEncontrado.get().getId()));
            }
        }
        return repository.save(model);
    }

    @Override
    public DadosCargo saveDto(DadosCargo dto) {
        Cargo cargo = save(dto);
        return converter.toDto(cargo);
    }

    @Override
    @Transactional
    public void delete(String controle) {
        Cargo cargo = findByControle(controle).get();
        cargo.excluir();
        repository.saveAndFlush(cargo);
    }

    @Override
    public Cargo update(String controle, DadosCargo dadosCargo) {
        Cargo cargo = findByControle(controle).get();
        validador.validate("ID", cargo.getId(), dadosCargo.id());
        cargo.setDescricao(dadosCargo.descricao());
        return repository.saveAndFlush(cargo);
    }

    @Override
    public DadosCargo updateDto(String controle, DadosCargo dto) {
        Cargo cargo = update(controle, dto);
        return converter.toDto(cargo);
    }
}
