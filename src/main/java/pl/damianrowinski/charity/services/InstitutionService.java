package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.charity.assemblers.InstitutionAssembler;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.domain.repositories.InstitutionRepository;
import pl.damianrowinski.charity.domain.resource.DonationResource;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;
import pl.damianrowinski.charity.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionAssembler institutionAssembler;
    private final DonationService donationService;

    public List<InstitutionResource> findAll() {
        List<Institution> institutionList = institutionRepository.findAll();
        return institutionAssembler.getResourceList(institutionList);
    }

    public InstitutionResource findResourceById(Long id) {
        return institutionAssembler.getResource(findById(id));
    }

    public InstitutionResource save(InstitutionResource institutionToSaveData) {
        Institution institutionToSave = institutionAssembler.getInstitution(institutionToSaveData);
        Institution savedInstitution = institutionRepository.save(institutionToSave);
        return institutionAssembler.getResource(savedInstitution);
    }

    public void delete(Long id) {
        Institution institution = findById(id);
        Optional<DonationResource> optionalDonationResource = donationService.findDonationByInstitution(institution);

        if (optionalDonationResource.isPresent())
            throw new ObjectInRelationshipException("exception.institution.in.relationship");
        institutionRepository.delete(institution);
    }

    Institution findById(Long id) {
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        if (optionalInstitution.isEmpty()) throw new ObjectNotFoundException("not.found.institution");
        return optionalInstitution.get();
    }
}
