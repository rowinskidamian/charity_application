package pl.damianrowinski.charity.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.resource.CategoryResource;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryAssembler {

    private final ModelMapper modelMapper;

    public List<CategoryResource> getResourceList(List<Category> categoryList) {
        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryResource.class))
                .collect(Collectors.toList());
    }

    public Category getCategory(CategoryResource categoryResource) {
        return modelMapper.map(categoryResource, Category.class);
    }

    public CategoryResource getCategoryResource(Category category) {
        return modelMapper.map(category, CategoryResource.class);
    }

    public Category getCategoryToAdd(CategoryResource categoryResource) {
        Category category = new Category();
        category.setName(categoryResource.getName());
        return category;
    }
}
