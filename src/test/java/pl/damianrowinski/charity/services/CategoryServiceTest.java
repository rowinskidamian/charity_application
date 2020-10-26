package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.repositories.CategoryRepository;
import pl.damianrowinski.charity.domain.resource.CategoryResource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
class CategoryServiceTest {

    private final CategoryService categoryService;

    @Test
    void shouldReturnListOfCategoriesResources() {
        //given
        List<Category> categoryList = List.of(new Category(), new Category());
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.findAll()).thenReturn(categoryList);

        //when
        categoryService.findAll();

        //then
        tutaj konczyc

    }

}