package pl.damianrowinski.charity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;
import pl.damianrowinski.charity.services.InstitutionService;

public class InstitutionConverter implements Converter<String, InstitutionResource> {

    private InstitutionService institutionService;

    @Override
    public InstitutionResource convert(String source) {
        return institutionService.findResourceById(Long.parseLong(source));
    }

    @Autowired
    public void setInstitutionService(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }
}
