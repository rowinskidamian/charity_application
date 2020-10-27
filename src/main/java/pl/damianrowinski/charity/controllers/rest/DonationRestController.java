package pl.damianrowinski.charity.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damianrowinski.charity.domain.repositories.DonationRepository;
import pl.damianrowinski.charity.domain.resource.DonationResource;

import java.util.List;

@RestController
@RequestMapping("/api/donation")
@RequiredArgsConstructor
public class DonationRestController {

    private final DonationRepository donationRepository;

    @GetMapping
    public List<DonationResource> getList () {
        return donationRepository.findAll();
    }

}
