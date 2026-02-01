package br.com.ueder.votoonline.controllers.v1;

import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.services.setores.SetorService;
import br.com.ueder.votoonline.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/v1/setores")
public class SetorController {

    private final SetorService service;

    public SetorController(SetorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DadosSetor>> getAll(){
        return ResponseEntity.ok(service.getAllDTO());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<DadosSetor> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findByIdDTO(id).get());
    }

    @GetMapping("/{controle:[0-9a-fA-F\\-]{36}}")
    public ResponseEntity<DadosSetor> getByControle(@PathVariable String controle){
        return ResponseEntity.ok(service.findByControleDTO(controle).get());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Validated DadosSetor entity){
        DadosSetor dadosSetor = service.saveDto(entity);
        return ResponseEntity.created(Util.getUri("/apis/v1/setores/{controle}", dadosSetor.controle()))
                .body(dadosSetor);
    }

    @PutMapping("/{controle}")
    public ResponseEntity<DadosSetor> update(
            @PathVariable String controle,
            @RequestBody @Validated DadosSetor dadosSetor
    ){
        DadosSetor setor = service.updateDto(controle, dadosSetor);
        return ResponseEntity.ok(setor);
    }

    @DeleteMapping("/{controle}")
    public ResponseEntity<DadosSetor> delete(@PathVariable String controle){
        service.delete(controle);
        return ResponseEntity.noContent().build();
    }
}
