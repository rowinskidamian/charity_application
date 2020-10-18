package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.domain.repositories.CategoryRepository;
import pl.damianrowinski.charity.domain.repositories.InstitutionRepository;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public Institution findById(Long id) {
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        if(optionalInstitution.isEmpty()) throw new ObjectNotFoundException("Institution not found");
        return optionalInstitution.get();
    }

    public void add(Institution institution) {
        institutionRepository.save(institution);
    }

    public void update(Institution institutionUpdateData) {
        Optional<Institution> optionalInstitution = institutionRepository.findById(institutionUpdateData.getId());
        if(optionalInstitution.isEmpty()) throw new ObjectNotFoundException("Institution not found");
        Institution institutionToUpdate = optionalInstitution.get();
        institutionToUpdate.setName(institutionUpdateData.getName());
        institutionToUpdate.setDescription(institutionUpdateData.getDescription());
        institutionRepository.save(institutionToUpdate);
    }

    public void delete(Long id){
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        if(optionalInstitution.isEmpty()) throw new ObjectNotFoundException("Institution not found");
        Institution institution = optionalInstitution.get();
        institutionRepository.delete(institution);
    }

    public List<Institution> findAll() {
        return institutionRepository.findAll();
    }
}
