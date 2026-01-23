package br.com.ueder.votoonline.controllers.v1;

import br.com.ueder.votoonline.converters.CargoConverter;
import br.com.ueder.votoonline.dtos.DadosCargo;
import br.com.ueder.votoonline.models.Cargo;
import br.com.ueder.votoonline.services.CargoService;
import br.com.ueder.votoonline.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/v1/cargos")
public class CargoController {

    private final CargoService service;
    private final CargoConverter converter;

    public CargoController(CargoService service, CargoConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<List<DadosCargo>> getAll(){
        List<DadosCargo> cargos = service.findAll()
                .stream()
                .map(converter::toDto).toList();
        return ResponseEntity.ok(cargos);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<DadosCargo> getById(@PathVariable Long id){
        return ResponseEntity.ok(converter.toDto(service.findById(id)));
    }

    @GetMapping("/{controle:[0-9a-fA-F\\-]{36}}")
    public ResponseEntity<DadosCargo> getByControle(@PathVariable String controle){
        return ResponseEntity.ok(converter.toDto(service.findByControle(controle)));
    }

    @PostMapping
    public ResponseEntity<DadosCargo> save(@RequestBody @Validated DadosCargo entity){
        Cargo cargo = service.save(entity);
        return ResponseEntity.created(Util.getUri("/apis/v1/cargos/{controle}",cargo.getControle()))
                .body(converter.toDto(cargo));
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
        Cargo cargo = service.update(controle, entity);
        return ResponseEntity.ok(converter.toDto(cargo));
    }


}
