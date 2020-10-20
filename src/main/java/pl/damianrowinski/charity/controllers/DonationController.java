package pl.damianrowinski.charity.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.damianrowinski.charity.domain.entities.Donation;
import pl.damianrowinski.charity.services.DonationService;

@Controller
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/donation/form")
    public String generateForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "donation_form";
    }

    @PostMapping("/donation/form")
    public String sendForm(Donation donation) {
        donationService.add(donation);
        return "redirect:/";
    }
}
