package com.github.pbciesla.recipesRating;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pbciesla.recipesRating.recipe.Difficult;
import com.github.pbciesla.recipesRating.recipe.PreparationTime;
import com.github.pbciesla.recipesRating.recipe.Recipe;
import com.github.pbciesla.recipesRating.recipe.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepositoryMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipe testRecipe;

    @Before
    public void prepareTestRecipe() {
        testRecipe = new Recipe("chocolate cake", "cake", "chocolateCake.com");
        testRecipe.setId(1);
        testRecipe.setDifficult(Difficult.MEDIUM);
        testRecipe.setPreparationTime(PreparationTime.LONG);
        testRecipe.setRate(5);
    }

    @Test
    public void createRecipeReturns201WhenInputIsValid() throws Exception {
        mockMvc.perform(post("/").contentType("application/json").
                content(objectMapper.writeValueAsString(testRecipe))).andExpect(status().isCreated());
    }

    @Test
    public void createRecipeReturns400WhenInputIsNull() throws Exception {
        mockMvc.perform(post("/").contentType("application/json").
                content(objectMapper.writeValueAsString(null))).andExpect(status().isBadRequest());
    }

    @Test
    public void getAllRecipesReturns200() throws Exception {
        List<Recipe> allRecipes = new ArrayList<>();
        allRecipes.add(testRecipe);
        when(recipeRepositoryMock.findAll()).thenReturn(allRecipes);
        mockMvc.perform(get("/all")).andExpect(status().isOk());
    }

    @Test
    public void findAllRecipesShouldReturnFoundRecipesEntries() throws Exception {
        List<Recipe> allRecipes = new ArrayList<>();
        allRecipes.add(testRecipe);
        when(recipeRepositoryMock.findAll()).thenReturn(allRecipes);
        mockMvc.perform(get("/all")).andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$", hasSize(1))).
                andExpect(jsonPath("$[0].name", is("chocolate cake"))).
                andExpect(jsonPath("$[0].category", is("cake"))).
                andExpect(jsonPath("$[0].link", is("chocolateCake.com"))).
                andExpect(jsonPath("$[0].difficult", is("MEDIUM"))).
                andExpect(jsonPath("$[0].preparationTime", is("LONG"))).
                andExpect(jsonPath("$[0].rate", is(5)));
    }

    @Test
    public void getRecipeByIdReturns200() throws Exception {
        when(recipeRepositoryMock.findById(1)).thenReturn(ofNullable(testRecipe));
        mockMvc.perform(get("/1")).andExpect(status().isOk());
    }

    @Test
    public void getRecipeByIdReturns404WhenNotFound() throws Exception {
        mockMvc.perform(get("/2")).andExpect(status().isNotFound());
    }

    @Test
    public void findRecipeByIdShouldReturnFoundRecipeEntry() throws Exception {
        when(recipeRepositoryMock.findById(1)).thenReturn(java.util.Optional.ofNullable(testRecipe));
        mockMvc.perform(get("/1")).andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.name", is("chocolate cake"))).
                andExpect(jsonPath("$.category", is("cake"))).
                andExpect(jsonPath("$.link", is("chocolateCake.com"))).
                andExpect(jsonPath("$.difficult", is("MEDIUM"))).
                andExpect(jsonPath("$.preparationTime", is("LONG"))).
                andExpect(jsonPath("$.rate", is(5)));
    }

    @Test
    public void getRecipeByCategoryReturns200() throws Exception {
        when(recipeRepositoryMock.findById(1)).thenReturn(java.util.Optional.ofNullable(testRecipe));
        mockMvc.perform(get("/category/cake")).andExpect(status().isOk());
    }

    @Test
    public void getRecipeByCategoryReturnsEmptyListWhenNotFound() throws Exception {
        mockMvc.perform(get("/category/dinner")).andExpect(content().string("[]"));
    }

    @Test
    public void deleteRecipeReturns204() throws Exception {
        mockMvc.perform(delete("/1")).andExpect(status().isNoContent());
    }

    @Test
    public void deleteRecipeReturns204WhenNotExists() throws Exception {
        mockMvc.perform(delete("/2")).andExpect(status().isNoContent());
    }

    @Test
    public void updateRecipeReturns200() throws Exception {
        Recipe newRecipe = new Recipe("chocolate cake", "cake", "chocolateCake.com");
        newRecipe.setDifficult(Difficult.MEDIUM);
        newRecipe.setPreparationTime(PreparationTime.LONG);
        newRecipe.setRate(4);
        mockMvc.perform(put("/1").contentType("application/json").
                content(objectMapper.writeValueAsString(newRecipe))).andExpect(status().isOk());
    }

}
