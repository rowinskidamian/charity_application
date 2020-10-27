package pl.damianrowinski.charity.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResource> getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResource getSingleCategory(@PathVariable Long id) {

        return categoryService.findById(id);
    }

    @PostMapping
    public CategoryResource saveCategory(@RequestBody CategoryResource categoryResource) {
        return categoryService.add(categoryResource);
    }

    @PutMapping("/{id}")
    public CategoryResource editCategory(@RequestBody CategoryResource categoryResource, @PathVariable Long id) {
        categoryResource.setId(id);
        return categoryService.update(categoryResource);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

}
