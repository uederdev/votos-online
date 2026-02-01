package br.com.ueder.votoonline.controllers.v1;

import br.com.ueder.votoonline.converters.entities.CargoConverter;
import br.com.ueder.votoonline.dtos.DadosCargo;
import br.com.ueder.votoonline.models.Cargo;
import br.com.ueder.votoonline.services.cargos.CargoService;
import br.com.ueder.votoonline.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/v1/cargos")
public class CargoController {

    private final CargoService service;

    public CargoController(CargoService service, CargoConverter converter) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DadosCargo>> getAll(){
        List<DadosCargo> cargos = service.getAllDTO();
        return ResponseEntity.ok(cargos);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<DadosCargo> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findByIdDTO(id).get());
    }

    @GetMapping("/{controle:[0-9a-fA-F\\-]{36}}")
    public ResponseEntity<DadosCargo> getByControle(@PathVariable String controle){
        return ResponseEntity.ok(service.findByControleDTO(controle).get());
    }

    @PostMapping
    public ResponseEntity<DadosCargo> save(@RequestBody @Validated DadosCargo entity){
        DadosCargo cargo = service.saveDto(entity);
        return ResponseEntity.created(Util.getUri("/apis/v1/cargos/{controle}",cargo.controle()))
                .body(cargo);
    }

    @DeleteMapping("/{controle}")
    public ResponseEntity<DadosCargo> delete(@PathVariable String controle){
        service.delete(controle);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{controle}")
    public ResponseEntity<DadosCargo> update(
            @PathVariable String controle,
            @RequestBody @Validated DadosCargo entity
    ){
        DadosCargo cargo = service.updateDto(controle, entity);
        return ResponseEntity.ok(cargo);
    }
}
