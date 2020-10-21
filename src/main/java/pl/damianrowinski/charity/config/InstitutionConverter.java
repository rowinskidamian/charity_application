package pl.damianrowinski.charity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.services.InstitutionService;

public class InstitutionConverter implements Converter<String, Institution> {

    private InstitutionService institutionService;

    @Override
    public Institution convert(String source) {
        return institutionService.findById(Long.parseLong(source));
    }

    @Autowired
    public void setInstitutionService(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }
}
