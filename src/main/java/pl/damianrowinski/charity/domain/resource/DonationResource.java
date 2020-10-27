package pl.damianrowinski.charity.domain.resource;

import lombok.Data;
import pl.damianrowinski.charity.domain.entities.Institution;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DonationResource {

    private Long id;
    private Integer quantity;
    private List<CategoryResource> categories;
    private Institution institution;
    private String streetName;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private LocalDate pickUpDate;
    private LocalTime pickUpTime;
    private String comment;

}
