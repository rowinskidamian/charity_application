package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.charity.assemblers.CategoryAssembler;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.repositories.CategoryRepository;
import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryAssembler categoryAssembler;

    public CategoryResource findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()) throw new ObjectNotFoundException("not.found.category");
        return categoryAssembler.getCategoryResource(optionalCategory.get());
    }

    public CategoryResource add(CategoryResource categoryResource) {
        Category category = categoryAssembler.getCategoryToAdd(categoryResource);
        Category savedCategory = categoryRepository.save(category);
        return categoryAssembler.getCategoryResource(savedCategory);
    }

    public void update(CategoryResource categoryUpdate) {
        CategoryResource categoryData = findById(categoryUpdate.getId());
        categoryData.setName(categoryUpdate.getName());
        Category categoryUpdated = categoryAssembler.getCategory(categoryData);
        categoryRepository.save(categoryUpdated);
    }

    public void delete(Long id){
        CategoryResource categoryData = findById(id);
        Category categoryToDelete = categoryAssembler.getCategory(categoryData);
        categoryRepository.delete(categoryToDelete);
    }

    public List<CategoryResource> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryAssembler.getResourceList(categoryList);
    }
}
