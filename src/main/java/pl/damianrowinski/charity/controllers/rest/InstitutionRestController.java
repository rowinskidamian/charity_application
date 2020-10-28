package pl.damianrowinski.charity.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.domain.repositories.InstitutionRepository;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;
import pl.damianrowinski.charity.services.InstitutionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/institution")
@Slf4j
@RequiredArgsConstructor
public class InstitutionRestController {

    private final InstitutionRepository institutionRepository;
    private final InstitutionService institutionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InstitutionResource> findAll() {
        return institutionService.findAll();
    }

    @GetMapping("/{id}")
    public InstitutionResource getOne(@PathVariable Long id) {
        return institutionService.findByIdForApi(id);
    }

    @PostMapping
    public InstitutionResource createOne(@Valid @RequestBody InstitutionResource institution) {
        return institutionService.save(institution);
    }

    @PutMapping("/{id}")
    public InstitutionResource updateOne(@RequestBody InstitutionResource institution, @PathVariable Long id) {
        InstitutionResource institutionResource = institutionService.findByIdForApi(id);
        if (institutionResource != null)
            institution.setId(id);
        return institutionService.save(institution);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        institutionService.delete(id);
    }

}
