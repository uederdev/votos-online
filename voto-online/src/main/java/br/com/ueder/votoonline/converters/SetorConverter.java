package br.com.ueder.votoonline.converters;

import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.models.Setor;
import org.springframework.stereotype.Component;

@Component
public class SetorConverter implements IConverter<Setor, DadosSetor>{

    @Override
    public Setor toModel(DadosSetor dto) {
        Setor setor = new Setor(dto.id(), dto.descricao());
        setor.setControle(dto.controle());
        return setor;
    }

    @Override
    public DadosSetor toDto(Setor model) {
        return new DadosSetor(model.getControle(), model.getId(), model.getDescricao());
    }
}
