package pl.damianrowinski.charity.services;

import org.junit.jupiter.api.BeforeEach;
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
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private CategoryAssembler categoryAssembler;
    private Category category1;
    private Category category2;

    @BeforeEach
    void init() {
        categoryRepository = mock(CategoryRepository.class);
        categoryAssembler = mock(CategoryAssembler.class);
        categoryService = new CategoryService(categoryRepository, categoryAssembler);
        category1 = new Category();
        category2 = new Category();
    }

    @Test
    void givenIdShouldReturnResource() {
        //given
        long id = 1L;
        category1.setId(id);
        CategoryResource categoryResource = modelMapper.map(category1, CategoryResource.class);
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(category1));
        when(categoryAssembler.getCategoryResource(category1)).thenReturn(categoryResource);

        //when
        CategoryResource categoryResourceFound = categoryService.findById(id);

        //then
        assertThat(categoryResourceFound).isEqualTo(categoryResource);

    }

    @Test
    void usingFindAllShouldReturnListOfCategoriesResources() {
        //given
        List<Category> categoryList = List.of(category1, category2);

        CategoryResource cat1res = modelMapper.map(category1, CategoryResource.class);
        CategoryResource cat2res = modelMapper.map(category2, CategoryResource.class);

        List<CategoryResource> categoryResourceList = List.of(cat1res, cat2res);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryAssembler.getResourceList(categoryList)).thenReturn(categoryResourceList);

        //when
        List<CategoryResource> returnedList = categoryService.findAll();

        //then
        assertThat(returnedList).isEqualTo(categoryResourceList);

    }

}