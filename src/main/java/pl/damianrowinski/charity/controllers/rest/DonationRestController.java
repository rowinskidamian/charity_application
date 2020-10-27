package pl.damianrowinski.charity.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public DonationResource findDonation(@PathVariable Long id) {
        return donationService.findByIdWithCategories(id);
    }

    @PostMapping
    public DonationResource saveDonation(@RequestBody DonationResource donationToSave) {
        return donationService.add(donationToSave);
    }



}
