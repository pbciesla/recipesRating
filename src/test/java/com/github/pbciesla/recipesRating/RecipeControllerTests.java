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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc.perform(get("/all")).andExpect(status().isOk());
    }

    @Test
    public void getRecipeByIdReturns200() throws Exception {
        when(recipeRepositoryMock.findById(1)).thenReturn(java.util.Optional.ofNullable(testRecipe));
        mockMvc.perform(get("/1")).andExpect(status().isOk());
    }

    @Test
    public void getRecipeByIdReturns404WhenNotFound() throws Exception {
        mockMvc.perform(get("/2")).andExpect(status().isNotFound());
    }

}
