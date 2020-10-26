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

    public Category findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()) throw new ObjectNotFoundException("not.found.category");
        return optionalCategory.get();
    }

    public void add(Category category) {
        categoryRepository.save(category);
    }

    public void update(Category categoryUpdate) {
        Category categoryToUpdate = findById(categoryUpdate.getId());
        categoryToUpdate.setName(categoryUpdate.getName());
        categoryRepository.save(categoryToUpdate);
    }

    public void delete(Long id){
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    public List<CategoryResource> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryAssembler.getResourceList(categoryList);
    }
}
