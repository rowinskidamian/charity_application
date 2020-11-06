package pl.damianrowinski.charity.assemblers;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.resource.CategoryResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class CategoryAssemblerTest {

    private CategoryAssembler categoryAssembler;
    private Category category;
    private String name;
    private CategoryResource categoryResource;
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        categoryAssembler = new CategoryAssembler(modelMapper);
        category = new Category();
        name = "test name";
        category.setName(name);
        categoryResource = new CategoryResource();
        categoryResource.setName(name);
    }

    @Test
    void shouldReturnResourcesListFromCategoryList() {
        List<Category> categoryList = getCategoryList();

        List<CategoryResource> expectedResourceList = categoryList.stream()
                .map(cat -> modelMapper.map(cat, CategoryResource.class))
                .collect(Collectors.toList());

        List<CategoryResource> returnedResourceList = categoryAssembler.getResourceList(categoryList);

        assertThat(returnedResourceList).isEqualTo(expectedResourceList);
    }

    @Test
    void shouldReturnResourceFromCategory() {
        CategoryResource returnedResource = categoryAssembler.getCategoryResource(category);

        assertThat(returnedResource).isEqualTo(categoryResource);
    }

    @Test
    void shouldReturnCategoryFromResource() {
        Category returnedCategory = categoryAssembler.getCategory(categoryResource);

        assertThat(returnedCategory).isEqualTo(category);
    }

    private List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        final int maxRandomStringLength = 5;
        final int noOfRandomCategories = 3;

        for (int i = 0; i < noOfRandomCategories; i++) {
            Category category = new Category();
            category.setName(RandomStringUtils.randomAlphabetic(maxRandomStringLength));
            categoryList.add(category);
        }

        return categoryList;
    }

}