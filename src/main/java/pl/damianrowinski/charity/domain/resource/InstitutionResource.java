package pl.damianrowinski.charity.domain.resource;

import lombok.Data;

@Data
public class InstitutionResource {
    private Long id;
    private String name;
    private String description;
}
