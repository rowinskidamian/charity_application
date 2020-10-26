package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.damianrowinski.charity.assemblers.CategoryAssembler;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.repositories.CategoryRepository;
import pl.damianrowinski.charity.domain.resource.CategoryResource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    void shouldReturnListOfCategoriesResources() {
        //given
        Category cat1 = new Category();
        cat1.setId(1);
        Category cat2 = new Category();
        cat2.setId(2);
        List<Category> categoryList = List.of(cat1, cat2);

        CategoryResource cat1res = modelMapper.map(cat1, CategoryResource.class);
        CategoryResource cat2res = modelMapper.map(cat2, CategoryResource.class);

        List<CategoryResource> categoryResourceList = List.of(cat1res, cat2res);

        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        CategoryAssembler categoryAssembler = mock(CategoryAssembler.class);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryAssembler.getResourceList(categoryList)).thenReturn(categoryResourceList);

        //when
        CategoryService categoryService = new CategoryService(categoryRepository, categoryAssembler);
        List<CategoryResource> returnedList = categoryService.findAll();

        //then
        assertThat(returnedList).isEqualTo(categoryResourceList);

    }

}