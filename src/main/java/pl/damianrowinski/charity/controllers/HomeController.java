package pl.damianrowinski.charity.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.services.InstitutionService;

import java.util.List;

@Controller

@RequiredArgsConstructor
public class HomeController {
    private final InstitutionService institutionService;

    @ModelAttribute("institutionsList")
    public List<Institution> institutionsList() {
        return institutionService.findAll();
    }

    @RequestMapping("/")
    public String homePage() {
        return "home";
    }


}
