package pl.damianrowinski.charity.mvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;
import pl.damianrowinski.charity.services.DonationService;
import pl.damianrowinski.charity.services.InstitutionService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final InstitutionService institutionService;
    private final DonationService donationService;

    @ModelAttribute("institutionsPairsList")
    public List<Pair<InstitutionResource, InstitutionResource>> institutionsList() {
        List<InstitutionResource> allInstitutions = institutionService.findAll();
        List<Pair<InstitutionResource, InstitutionResource>> instPairList = new ArrayList<>();

        for (int i = 1; i <= allInstitutions.size(); i++) {
            InstitutionResource institution1 = allInstitutions.get(i - 1);
            InstitutionResource institution2;

            if (i < allInstitutions.size())
                institution2 = allInstitutions.get(i);
            else
                institution2 = new InstitutionResource();
            instPairList.add(Pair.of(institution1, institution2));
            i++;
        }
        return instPairList;
    }

    @ModelAttribute("countDonations")
    public long countDonations() {
        return donationService.countDonations();
    }

    @ModelAttribute("countBags")
    public long countBags() {
        return donationService.countBags();
    }

    @RequestMapping("/")
    public String homePage() {
        return "home";
    }
}
