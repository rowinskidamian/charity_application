package pl.damianrowinski.charity.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.charity.domain.entities.Institution;
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
    public List<List<Institution>> institutionsList() {
        List<Institution> allInstitutions = institutionService.findAll();
        List<Institution> institutionsPair = new ArrayList<>();
        List<List<Institution>> instPairList = new ArrayList<>();

        for (int i = 1; i <= allInstitutions.size(); i++) {
            if(i%2 != 0) {
                institutionsPair = new ArrayList<>();
                institutionsPair.add(allInstitutions.get(i-1));
            } else {
                institutionsPair.add(allInstitutions.get(i-1));
                instPairList.add(institutionsPair);
            }
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
