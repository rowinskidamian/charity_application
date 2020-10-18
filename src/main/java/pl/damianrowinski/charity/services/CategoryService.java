package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.repositories.CategoryRepository;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()) throw new ObjectNotFoundException("Category not found");
        return optionalCategory.get();
    }

    public void add(Category category) {
        categoryRepository.save(category);
    }

    public void update(Category categoryUpdateData) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryUpdateData.getId());
        if(optionalCategory.isEmpty()) throw new ObjectNotFoundException("Category not found");
        Category categoryToUpdate = optionalCategory.get();
        categoryToUpdate.setName(categoryUpdateData.getName());
        categoryRepository.save(categoryToUpdate);
    }

    public void delete(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()) throw new ObjectNotFoundException("Category not found");
        Category category = optionalCategory.get();
        categoryRepository.delete(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
