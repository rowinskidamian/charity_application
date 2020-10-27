package pl.damianrowinski.charity.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damianrowinski.charity.domain.resource.DonationResource;
import pl.damianrowinski.charity.services.DonationService;

import java.util.List;

@RestController
@RequestMapping("/api/donation")
@RequiredArgsConstructor
public class DonationRestController {

    private final DonationService donationService;

    @GetMapping
    public List<DonationResource> getList () {
        return donationService.findAllWithCategories();
    }

}
