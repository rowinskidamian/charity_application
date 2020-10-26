package pl.damianrowinski.charity.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @GetMapping()
    public List<CategoryResource> getCategories() {
        return categoryService.findAll();
    }

}
