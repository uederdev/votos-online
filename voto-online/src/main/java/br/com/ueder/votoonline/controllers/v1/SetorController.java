package br.com.ueder.votoonline.controllers.v1;

import br.com.ueder.votoonline.converters.SetorConverter;
import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.models.Setor;
import br.com.ueder.votoonline.services.SetorService;
import br.com.ueder.votoonline.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/v1/setores")
public class SetorController {

    private final SetorConverter converter;
    private final SetorService service;

    public SetorController(SetorConverter converter, SetorService service) {
        this.converter = converter;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DadosSetor>> getAll(){
        return ResponseEntity.ok(service.findAll()
                .stream()
                .map(converter::toDto).toList());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<DadosSetor> getById(@PathVariable Long id){
        return ResponseEntity.ok(converter.toDto(service.findById(id)));
    }

    @GetMapping("/{controle:[0-9a-fA-F\\-]{36}}")
    public ResponseEntity<DadosSetor> getByControle(@PathVariable String controle){
        return ResponseEntity.ok(converter.toDto(service.findByControle(controle)));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Validated DadosSetor entity){
        Setor setor = service.save(entity);
        return ResponseEntity.created(Util.getUri("/apis/v1/setores/{controle}", setor.getControle()))
                .body(converter.toDto(setor));
    }

    @PutMapping("/{controle}")
    public ResponseEntity<DadosSetor> update(
            @PathVariable String controle,
            @RequestBody @Validated DadosSetor dadosSetor
    ){
        Setor setor = service.update(controle, dadosSetor);
        return ResponseEntity.ok(converter.toDto(setor));
    }

    @DeleteMapping("/{controle}")
    public ResponseEntity<DadosSetor> delete(@PathVariable String controle){
        service.delete(controle);
        return ResponseEntity.noContent().build();
    }
}
