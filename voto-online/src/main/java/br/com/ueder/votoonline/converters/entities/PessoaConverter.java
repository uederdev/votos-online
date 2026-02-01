package br.com.ueder.votoonline.converters.entities;

import br.com.ueder.votoonline.converters.ConverterImpl;
import br.com.ueder.votoonline.dtos.pessoa.DadosPessoa;
import br.com.ueder.votoonline.models.Pessoa;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
public class PessoaConverter extends ConverterImpl<Pessoa, DadosPessoa> {

    public Pessoa toModel(DadosPessoa dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(dto.id());
        pessoa.setControle(dto.controle());
        pessoa.setDataAdmissao(dto.dataAdmissao());
        pessoa.setDataRescisao(dto.dataRescisao());
        pessoa.setNomeCompleto(dto.nomeCompleto());
        pessoa.setPathFoto(dto.pathFoto());
        pessoa.setMatricula(dto.matricula());
        return pessoa;
    }

    public DadosPessoa toDto(Pessoa model) {
        return new DadosPessoa(model.getControle(), model.getId(), model.getNomeCompleto(), model.getDataAdmissao(),
                model.getDataRescisao(), model.getMatricula(), model.getPathFoto());
    }

    public DadosPessoa toDtoLink(Pessoa model) {
        String uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/apis/v1/pessoas/"+ model.getControle() +"/images" )
                .toUriString();

        return new DadosPessoa(model.getControle(), model.getId(), model.getNomeCompleto(), model.getDataAdmissao(),
                model.getDataRescisao(), model.getMatricula(), uri);
    }

    public List<DadosPessoa> toDtoLink(List<Pessoa> models) {
        return models.stream()
                .map(this::toDtoLink).toList();
    }
}
