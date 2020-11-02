package pl.damianrowinski.charity.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;

import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;
import pl.damianrowinski.charity.services.CategoryService;
import pl.damianrowinski.charity.services.InstitutionService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CategoryRestController.class)
class CategoryRestControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private CategoryService categoryService;
    @MockBean private InstitutionService institutionService;

    private List<CategoryResource> categoryResourceList;
    private CategoryResource resource1;
    private CategoryResource resource2;
    private long categoryId;

    @BeforeEach
    void init() {
        categoryId = 1;
        resource1 = new CategoryResource();
        resource1.setName("Testowa kategoria1");
        resource1.setId(1L);
        resource2 = new CategoryResource();
        resource2.setName("Testowa kategoria2");
        resource2.setId(2L);

        categoryResourceList = List.of(resource1, resource2);
    }

    @Test
    void givenNoIdShouldGetResourceList() throws Exception {
        String categoryListJson = objectMapper.writeValueAsString(categoryResourceList);

        when(categoryService.findAll()).thenReturn(categoryResourceList);
        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(categoryListJson));
    }

    @Test
    void givenIdShouldGetResource() throws Exception {
        resource1.setId(categoryId);
        when(categoryService.findById(categoryId)).thenReturn(resource1);

        mockMvc.perform(get("/api/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(resource1.getName())));
    }

    @Test
    void givenCategoryResourceShouldCreateNew() throws Exception {
        when(categoryService.add(any(CategoryResource.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(resource1.getName())));
    }

    @Test
    void givenCategoryResourceShouldUpdate() throws Exception{
        final int id = 1;

        when(categoryService.update(any(CategoryResource.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/api/category/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(resource1));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(resource1.getName())))
                .andExpect(jsonPath("$.id", Matchers.is(id)));
    }

    @Test
    void shouldReturn404WhenObjectFindByIdNotFound() throws Exception {
        when(categoryService.findById(categoryId)).thenThrow(ObjectNotFoundException.class);

        mockMvc.perform(get("/api/category/{id}", categoryId))
                .andExpect(status().isNotFound());
    }

    @Test

    void shouldReturnOkWhenDeleteOnGivenPath() throws Exception{
        CategoryService categoryService = mock(CategoryService.class);

        doNothing().when(categoryService).delete(categoryId);

        mockMvc.perform(delete("/api/category/{id}", categoryId))
                .andExpect(status().isOk());
    }



}