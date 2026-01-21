package br.com.ueder.votoonline.controllers.v1;

import br.com.ueder.votoonline.converters.SetorConverter;
import br.com.ueder.votoonline.dtos.DadosSetor;
import br.com.ueder.votoonline.models.Setor;
import br.com.ueder.votoonline.services.SetorService;
import br.com.ueder.votoonline.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/v1/setores")
public class SetorController {

    @Autowired
    private SetorConverter converter;
    @Autowired
    private SetorService service;

    @GetMapping
    public ResponseEntity<List<DadosSetor>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosSetor> getById(@PathVariable Long id){
        return ResponseEntity.ok(converter.toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DadosSetor entity){
        Setor setor = service.save(entity);
        return ResponseEntity.created(Util.getUri("/apis/v1/setores/{id}", setor.getControle()))
                .body(converter.toDto(setor));
    }
}
