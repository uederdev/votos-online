package br.com.ueder.votoonline.services.pessoas;

import br.com.ueder.votoonline.converters.entities.PessoaConverter;
import br.com.ueder.votoonline.dtos.pessoa.DadosPessoa;
import br.com.ueder.votoonline.exceptions.ObjectNotFoundException;
import br.com.ueder.votoonline.exceptions.RNException;
import br.com.ueder.votoonline.exceptions.RNRegistroDuplicadoException;
import br.com.ueder.votoonline.models.Pessoa;
import br.com.ueder.votoonline.repositories.PessoaRepository;
import br.com.ueder.votoonline.services.IService;
import br.com.ueder.votoonline.services.images.IFileStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService implements IService<Pessoa, DadosPessoa> {

    private final PessoaRepository repository;
    private final PessoaConverter converter;
    private final IFileStorage fileStorage;

    public PessoaService(PessoaRepository repository, PessoaConverter converter, IFileStorage fileStorage) {
        this.repository = repository;
        this.converter = converter;
        this.fileStorage = fileStorage;
    }

    @Override
    public List<Pessoa> findAll() {
        return repository.findAll().stream().toList();
    }

    @Override
    public List<DadosPessoa> getAllDTO() {
        return converter.toDtoLink(repository.findAll());
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.valueOf(id))));
    }

    @Override
    public Optional<DadosPessoa> findByIdDTO(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Pessoa> findByControle(String controle) {
        return Optional.ofNullable(repository.findByControle(controle).orElseThrow(() -> new ObjectNotFoundException(controle)));
    }

    @Override
    public Optional<DadosPessoa> findByControleDTO(String controle) {
        return Optional.empty();
    }

    @Override
    public Pessoa save(DadosPessoa dto) {
        Optional<Pessoa> pessoaEncontrada = repository.findById(dto.id());
        if (pessoaEncontrada.isPresent()) {
//            boolean existsImage = fileStorage.existsImage(dto.pathFoto());
//            if (existsImage) {
//                fileStorage.deleteImage(dto.pathFoto());
//            }
            throw new RNRegistroDuplicadoException(dto.id().toString());
        }
        pessoaEncontrada = repository.findByMatricula(dto.matricula());
        if (pessoaEncontrada.isPresent()) {
//            boolean existsImage = fileStorage.existsImage(dto.pathFoto());
//            if (existsImage) {
//                fileStorage.deleteImage(dto.pathFoto());
//            }
            throw new RNException("A matrícula " + dto.matricula() + " já cadastrada no sistema");
        }
        return repository.save(converter.toModel(dto));
    }

    @Override
    @Transactional
    public DadosPessoa saveDto(DadosPessoa dto) {
        Pessoa pessoa = save(dto);
        return converter.toDto(pessoa);
    }

    @Override
    public void delete(String controle) {
        Pessoa pessoa = findByControle(controle).get();
        pessoa.excluir();
        fileStorage.deleteImage(pessoa.getPathFoto());
        repository.save(pessoa);
    }

    @Override
    @Transactional
    public Pessoa update(String controle, DadosPessoa dto) {
        Pessoa pessoaEncontrada = findByControle(controle).get();
        if (!pessoaEncontrada.getMatricula().equals(dto.matricula())){
            throw new RNException("Não é possível alterar a matrícula " + pessoaEncontrada.getId());
        }
        pessoaEncontrada.setPathFoto(dto.pathFoto());
        pessoaEncontrada.setNomeCompleto(dto.nomeCompleto());
        if (pessoaEncontrada.getDataRescisao() != null){
            throw new RNException("Não é possível alterar a data de rescisão de uma pessoa já desligada");
        }
        pessoaEncontrada.setDataRescisao(dto.dataRescisao());
        return repository.save(pessoaEncontrada);
    }

    @Override
    public DadosPessoa updateDto(String controle, DadosPessoa dto) {
        return null;
    }

    @Transactional
    public DadosPessoa save(DadosPessoa dadosPessoa, MultipartFile file) {
            boolean existsImage = fileStorage.existsImage(file.getOriginalFilename());
            if (existsImage) {
                fileStorage.deleteImage(file);
            }
            fileStorage.saveImage(file);
            Pessoa pessoa = converter.toModel(dadosPessoa);
            pessoa.setPathFoto(file.getOriginalFilename());
            return saveDto(converter.toDto(pessoa));
    }

}
