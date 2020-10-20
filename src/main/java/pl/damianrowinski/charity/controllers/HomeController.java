package pl.damianrowinski.charity.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.damianrowinski.charity.services.CategoryService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping
    public String homePage() {
        return "home";
    }

    @GetMapping("exception")
    @ResponseBody
    public String exception(@RequestParam Long id) {
        return categoryService.findById(id).toString();
    }
}
