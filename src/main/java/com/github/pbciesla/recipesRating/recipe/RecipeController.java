package com.github.pbciesla.recipesRating.recipe;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping(path = "/all")
    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping(path = "/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer recipeId) {
        return recipeRepository.findById(recipeId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    /*
    @GetMapping(path = "/{recipeCategory}")
    public Iterable<Recipe> getRecipeByCategory(@PathVariable String recipeCategory) {
    }*/

    @PostMapping(path = "/")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
