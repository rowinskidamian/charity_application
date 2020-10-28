package pl.damianrowinski.charity.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InstitutionAssembler {
    private final ModelMapper modelMapper;


    public List<InstitutionResource> getResourceList(List<Institution> listToConvert) {
        return listToConvert.stream()
                .map(institution -> modelMapper.map(institution, InstitutionResource.class))
                .collect(Collectors.toList());
    }

    public InstitutionResource getResource(Institution institution) {
        return modelMapper.map(institution, InstitutionResource.class);
    }

    public Institution getInstitution(InstitutionResource institutionData) {
        return modelMapper.map(institutionData, Institution.class);
    }
}
