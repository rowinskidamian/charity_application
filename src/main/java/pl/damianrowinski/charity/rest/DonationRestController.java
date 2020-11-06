package pl.damianrowinski.charity.rest;

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

    @PutMapping("/{id}")
    public DonationResource editDonation(@RequestBody DonationResource donationToUpdate, @PathVariable Long id) {
        donationToUpdate.setId(id);
        return donationService.update(donationToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteDonation(@PathVariable Long id) {
        donationService.delete(id);
    }


}
