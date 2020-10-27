package pl.damianrowinski.charity.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.domain.repositories.InstitutionRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController // @Controller + @ResponseBody na wszystkich metoda
@RequestMapping("/api/institutions") // ścieżka wyraża jaki zasób przetwarzamy
@Slf4j @RequiredArgsConstructor
public class InstitutionRestController {

    private final InstitutionRepository institutionRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK) // domyślny kod odpowiedzi gdy nie ma wyjątku
    public List<Institution> findAll() {
        return institutionRepository.findAll();
    }

    @PostMapping // służy do utworzenia zasobu danego typu, gdzie sam zasób nadaje identyfikator
    // Utworzenie zasobu powinno zakończyć się kodem HTTP 201 Created z nagłówkiem Location
    // wskazującym adres tego zasobu
    // @Valid powoduje kod 400 Bad Request
    public ResponseEntity createOne(@Valid @RequestBody Institution institution) {
        institutionRepository.save(institution);
        return ResponseEntity
                .created(URI.create("/api/institutions/" + institution.getId()))
                .build();
    }

    @GetMapping("/{id}")
    // 200 OK + w treści zasób
    // 404 Not Found
    public ResponseEntity getOne(@PathVariable Long id) {
        return institutionRepository.findById(id)
                .map(ins -> ResponseEntity.ok().body(ins))
                .orElse(ResponseEntity.notFound().build());
    }

    // @PostMapping("/{id}/delete")
    // 204 No content i bez treści
    // 404 Not found
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) {
        return institutionRepository
                .findById(id)
                .stream()
                .peek(institutionRepository::delete)
                .findAny()
                .map(ins -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    // 200 OK
    // 404 Not found - gdy nie ma takiego id
    @PutMapping("/{id}")
    public ResponseEntity updateOne(@RequestBody Institution institution, @PathVariable Long id) {
        institution.setId(id);
        if (!institutionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        institutionRepository.save(institution);
        return ResponseEntity.ok().build();
    }


}
