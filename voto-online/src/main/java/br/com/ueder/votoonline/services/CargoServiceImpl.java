package br.com.ueder.votoonline.services;

import br.com.ueder.votoonline.converters.CargoConverter;
import br.com.ueder.votoonline.dtos.DadosCargo;
import br.com.ueder.votoonline.exceptions.ObjectNotFoundException;
import br.com.ueder.votoonline.exceptions.RNException;
import br.com.ueder.votoonline.exceptions.RNRegistroDuplicadoException;
import br.com.ueder.votoonline.models.Cargo;
import br.com.ueder.votoonline.repositories.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CargoServiceImpl implements CargoService{

    private final CargoRepository repository;
    private final CargoConverter converter;

    public CargoServiceImpl(CargoRepository repository, CargoConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public List<Cargo> findAll() {
        return repository.findAll();
    }

    @Override
    public Cargo findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.valueOf(id)));
    }

    @Override
    public Cargo findByControle(String controle) {
        return repository.findByControle(controle)
                .orElseThrow(() -> new ObjectNotFoundException(controle));
    }

    @Override
    @Transactional
    public Cargo save(DadosCargo entity) {
        Optional<Cargo> cargoEncontrado = repository.findById(entity.id());
        if (cargoEncontrado.isPresent()){
            throw new RNRegistroDuplicadoException(String.valueOf(cargoEncontrado.get().getId()));
        }
        return repository.save(converter.toModel(entity));
    }

    @Override
    @Transactional
    public void delete(String controle) {
        Cargo cargo = findByControle(controle);
        cargo.excluir();
        repository.saveAndFlush(cargo);
    }

    @Override
    public Cargo update(String controle, DadosCargo dadosCargo) {
        Cargo cargo = findByControle(controle);
        if (!cargo.getId().equals(dadosCargo.id())){
            throw new RNException("Não é possível alterar o ID " + cargo.getId());
        }
        cargo.setDescricao(dadosCargo.descricao());
        return repository.saveAndFlush(cargo);
    }
}
