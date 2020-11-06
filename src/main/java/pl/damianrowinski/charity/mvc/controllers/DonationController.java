package pl.damianrowinski.charity.mvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.charity.domain.entities.Donation;
import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.domain.resource.DonationResource;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;
import pl.damianrowinski.charity.services.CategoryService;
import pl.damianrowinski.charity.services.DonationService;
import pl.damianrowinski.charity.services.InstitutionService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;

    @GetMapping("/donation/form")
    public String generateForm(Model model) {
        List<CategoryResource> categoryList = categoryService.findAll();
        List<InstitutionResource> institutionList = institutionService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("institutionList", institutionList);
        model.addAttribute("donation", new Donation());
        return "donation_form";
    }

    @PostMapping("/donation/form")
    public String sendForm(@ModelAttribute("donation") DonationResource donation) {
        donationService.add(donation);
        return "donation_form_confirmation";
    }

    @GetMapping("/donation/find/{id}")
    @ResponseBody
    public String findDonation(@PathVariable Long id) {
        CategoryResource serviceById = categoryService.findById(id);
        return serviceById.toString();
    }
}
