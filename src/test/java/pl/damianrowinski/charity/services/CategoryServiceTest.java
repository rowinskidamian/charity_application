package pl.damianrowinski.charity.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import pl.damianrowinski.charity.assemblers.CategoryAssembler;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.repositories.CategoryRepository;
import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import java.util.List;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private ModelMapper modelMapper = new ModelMapper();
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private CategoryAssembler categoryAssembler;
    private DonationService donationService;
    private Category category1;
    private Category category2;

    @BeforeEach
    void init() {
        categoryRepository = mock(CategoryRepository.class);
        categoryAssembler = mock(CategoryAssembler.class);
        donationService = mock(DonationService.class);
        categoryService = new CategoryService(categoryRepository, categoryAssembler, donationService);
        category1 = new Category();
        category2 = new Category();
    }

    @DisplayName("Testing method findById(Long id)")
    @Nested
    class FindByIdMethod {
        @Test
        void givenIdShouldReturnResource() {
            //given
            long id = 1L;
            category1.setId(id);
            CategoryResource categoryResource = modelMapper.map(category1, CategoryResource.class);
            when(categoryRepository.findById(id)).thenReturn(ofNullable(category1));
            when(categoryAssembler.getCategoryResource(category1)).thenReturn(categoryResource);

            //when
            CategoryResource categoryResourceFound = categoryService.findById(id);

            //then
            assertThat(categoryResourceFound).isEqualTo(categoryResource);
        }

        @Test
        void givenAnyIdNotPresentShouldThrowObjectNotFoundException() {
            when(categoryRepository.findById(1L)).thenReturn(ofNullable(category1));

            assertThatThrownBy(() -> categoryService.findById(2L))
                    .isInstanceOf(ObjectNotFoundException.class);
        }

    }

    @DisplayName("Testing add(CategoryResource categoryResource) method")
    @Nested
    class Add {
        @Test
        void shouldUseCategoryAssemblerToGetCategory() {
            categoryService.add(ArgumentMatchers.any());

            Mockito.verify(categoryAssembler, Mockito.times(1))
                    .getCategoryToAdd(ArgumentMatchers.any());
        }

        @Test
        void shouldUseCategoryAssemblerToGetCategoryResource() {
            categoryService.add(ArgumentMatchers.any());

            Mockito.verify(categoryAssembler, Mockito.times(1))
                    .getCategoryResource(ArgumentMatchers.any());
        }

        @Test
        void givenResourceWithoutIdShouldReturnSavedWithId(){
            //given
            category2 = modelMapper.map(category1, Category.class);
            category2.setId(1L);

            CategoryResource resourceNoId = modelMapper.map(category1, CategoryResource.class);
            CategoryResource resourceWithId = modelMapper.map(category2, CategoryResource.class);

            when(categoryAssembler.getCategoryToAdd(resourceNoId)).thenReturn(category1);
            when(categoryRepository.save(category1)).thenReturn(category2);
            when(categoryAssembler.getCategoryResource(category2)).thenReturn(resourceWithId);

            //when
            CategoryResource returnedResource = categoryService.add(resourceNoId);

            //then
            assertThat(returnedResource).isEqualTo(resourceWithId);
        }
    }

    @DisplayName("Testing update(CategoryResource cs) method")
    @Nested
    class Update{
        @Test
        void givenResourceToUpdateShouldReturnUpdated() {
            CategoryResource categoryResource = modelMapper.map(category1, CategoryResource.class);
            categoryResource.setId(1L);
            category1.setId(1L);
            when(categoryRepository.findById(1L)).thenReturn(ofNullable(category1));
            when(categoryAssembler.getCategoryResource(category1)).thenReturn(categoryResource);
            when(categoryAssembler.getCategory(categoryResource)).thenReturn(category1);
            when(categoryRepository.save(category1)).thenReturn(category1);

            CategoryResource returned = categoryService.update(categoryResource);

            assertThat(returned).isEqualTo(categoryResource);
        }
    }

    @DisplayName("Testing delete(Long id)")
    @Nested
    class Delete{
        @Test
        void shouldUseDeleteInRepository() {
            CategoryResource categoryResource = modelMapper.map(category1, CategoryResource.class);
            categoryResource.setId(1L);
            category1.setId(1L);
            when(categoryRepository.findById(1L)).thenReturn(ofNullable(category1));
            when(categoryAssembler.getCategoryResource(category1)).thenReturn(categoryResource);

            categoryService.delete(1L);

            Mockito.verify(categoryRepository, Mockito.atLeastOnce()).delete(ArgumentMatchers.any());
        }
    }

    @DisplayName("Testing findAll() method")
    @Nested
    class FindAll {
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


}