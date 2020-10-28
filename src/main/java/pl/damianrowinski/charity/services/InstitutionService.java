package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.charity.assemblers.InstitutionAssembler;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.domain.repositories.InstitutionRepository;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundApiException;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionAssembler institutionAssembler;

    public List<InstitutionResource> findAll() {
        List<Institution> institutionList = institutionRepository.findAll();
        return institutionAssembler.getResourceList(institutionList);
    }

    public InstitutionResource findByIdForApi(Long id) {
        return institutionRepository.findById(id)
                .map(institutionAssembler::getResource)
                .orElseThrow(() -> new ObjectNotFoundApiException("Institution not found for id:" + id));
    }

    public InstitutionResource save(InstitutionResource institutionToSaveData) {
        Institution institutionToSave =  institutionAssembler.getInstitution(institutionToSaveData);
        Institution savedInstitution = institutionRepository.save(institutionToSave);
        return institutionAssembler.getResource(savedInstitution);
    }

    public void delete(Long id) {
        institutionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundApiException("Institution not found for id:" + id));
    }
}
