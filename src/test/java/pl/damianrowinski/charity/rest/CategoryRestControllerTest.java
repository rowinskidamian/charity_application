package pl.damianrowinski.charity.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.services.CategoryService;
import pl.damianrowinski.charity.services.InstitutionService;

import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest(controllers = CategoryRestController.class)
class CategoryRestControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private InstitutionService institutionService;

    private List<CategoryResource> categoryResourceList;
    private CategoryResource resource1;
    private CategoryResource resource2;

    @BeforeEach
    void init() {
        resource1 = new CategoryResource();
        resource1.setName("Testowa kategoria1");
        resource1.setId(1L);
        resource2 = new CategoryResource();
        resource2.setName("Testowa kategoria2");
        resource2.setId(2L);

        categoryResourceList = List.of(resource1, resource2);
    }

    @Test
    void shouldGetResourceList() throws Exception {
        when(categoryService.findAll()).thenReturn(categoryResourceList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/category"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void givenIdShouldGetResource() throws Exception {
        final long categoryId = 1L;
        resource1.setId(categoryId);
        when(categoryService.findById(categoryId)).thenReturn(resource1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/category/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(resource1.getName())));
    }

    @Test
    void givenCategoryResourceShouldCreateNew() throws Exception {
        when(categoryService.add(any(CategoryResource.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(resource1.getName())));
    }


}