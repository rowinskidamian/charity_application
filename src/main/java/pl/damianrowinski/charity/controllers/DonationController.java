package pl.damianrowinski.charity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.damianrowinski.charity.domain.entities.Donation;

@Controller

public class DonationController {

    @GetMapping("/donation/form")
    public String generateForm(Model model) {
        Donation donation = new Donation();
        model.addAttribute("donation", donation);
        return "donation_form";
    }
}
