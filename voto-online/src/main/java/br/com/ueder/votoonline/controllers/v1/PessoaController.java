package br.com.ueder.votoonline.controllers.v1;

import br.com.ueder.votoonline.converters.entities.PessoaConverter;
import br.com.ueder.votoonline.dtos.pessoa.DadosPessoa;
import br.com.ueder.votoonline.exceptions.RNException;
import br.com.ueder.votoonline.models.Pessoa;
import br.com.ueder.votoonline.services.images.IFileStorage;
import br.com.ueder.votoonline.services.pessoas.PessoaService;
import br.com.ueder.votoonline.utils.Util;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/apis/v1/pessoas")
public class PessoaController {

    private final PessoaService service;
    private final IFileStorage fileStorage;

    public PessoaController(PessoaService service, IFileStorage fileStorage) {
        this.service = service;
        this.fileStorage = fileStorage;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(service.getAllDTO());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<DadosPessoa> findById(@PathVariable Long id) {
//        return ResponseEntity.ok().build();
//    }

    @PostMapping
    public ResponseEntity<DadosPessoa> save(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute DadosPessoa dadosPessoa
    ){
        if (file.getOriginalFilename().equals("") && file == null && file.isEmpty()) {
            throw new RNException("Imagem não selecionada.");
        }
        DadosPessoa pessoa = service.save(dadosPessoa, file);
        return ResponseEntity.created(Util.getUri("/apis/v1/pessoas/{controle}", pessoa.controle())).build();
    }

    @DeleteMapping("/{controle}")
    public ResponseEntity<DadosPessoa> delete(@PathVariable String controle) {
        service.delete(controle);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{controle}/images")
    public ResponseEntity<Resource> getImage(@PathVariable String controle) {
        try {
            Optional<Pessoa> pessoa = service.findByControle(controle);
            if (pessoa.isPresent()) {
                Path filePath = Paths.get(fileStorage.getPathDirectory()).resolve(pessoa.get().getPathFoto());
                Resource resource = new UrlResource(filePath.toUri());
                if (!resource.exists()) {
                    return ResponseEntity.notFound().build();
                }
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
                        .body(resource);
            }else{
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

