package br.com.ueder.votoonline.converters;

import br.com.ueder.votoonline.dtos.DadosCargo;
import br.com.ueder.votoonline.models.Cargo;
import org.springframework.stereotype.Component;

@Component
public class CargoConverter implements IConverter<Cargo, DadosCargo>{

    @Override
    public Cargo toModel(DadosCargo dto) {
        Cargo cargo = new Cargo(dto.id(), dto.descricao());
        cargo.setControle(dto.controle());
        return cargo;
    }

    @Override
    public DadosCargo toDto(Cargo model) {
        return new DadosCargo(model.getControle(), model.getId(), model.getDescricao());
    }
}
